package com.hcmute.myanime.service;

import com.hcmute.myanime.dto.LogHistoryDTO;
import com.hcmute.myanime.exception.BadRequestException;
import com.hcmute.myanime.model.*;
import com.hcmute.myanime.repository.EpisodeRepository;
import com.hcmute.myanime.repository.LogHistoriesRepository;
import com.hcmute.myanime.repository.MovieSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class LogHistoriesService {
    @Autowired
    private LogHistoriesRepository logHistoriesRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private MovieSeriesRepository movieSeriesRepository;

    public List<LogHistoriesEntity> getByUserLogging()
    {
        List<LogHistoriesEntity> historiesEntityList =
                userService.findUserLogging()
                .getLogHistoriesEntityCollection().stream().toList();
        return historiesEntityList;
    }

    public boolean save(LogHistoryDTO logHistoryDTO) {
        Optional<EpisodeEntity> episodeEntityOptional = episodeRepository.findById(logHistoryDTO.getEpisode_id());
        if(!episodeEntityOptional.isPresent()) {
            throw new BadRequestException("Episode not found");
        }
        EpisodeEntity episodeEntity = episodeEntityOptional.get();
//        Optional<MovieSeriesEntity> movieSeriesEntityOptional = movieSeriesRepository.findById(logHistoryDTO.getSeries_id());
//        if(!movieSeriesEntityOptional.isPresent()) {
//            throw new BadRequestException("Series not found");
//        }
//        MovieSeriesEntity movieSeriesEntity = movieSeriesEntityOptional.get();
        UsersEntity userLogging = userService.findUserLogging();

        LogHistoriesEntity logHistoriesEntity = logHistoriesRepository
                .findByUserAndMovieSeriesEntity(userLogging, episodeEntity.getMovieSeriesBySeriesId());
        if(logHistoriesEntity == null) {
            logHistoriesEntity = new LogHistoriesEntity();
        }
        logHistoriesEntity.setLastSecond(logHistoryDTO.getLastSecond());
        logHistoriesEntity.setEpisodeEntity(episodeEntity);
        logHistoriesEntity.setMovieSeriesEntity(episodeEntity.getMovieSeriesBySeriesId());
        logHistoriesEntity.setUser(userLogging);
        logHistoriesEntity.setCreateAt(new Timestamp(System.currentTimeMillis()));
        try
        {
            logHistoriesRepository.save(logHistoriesEntity);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public boolean deleteById(int historyId) {
        try {
            logHistoriesRepository.deleteById(historyId);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
