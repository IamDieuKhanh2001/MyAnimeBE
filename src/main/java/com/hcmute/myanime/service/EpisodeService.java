package com.hcmute.myanime.service;

import com.hcmute.myanime.dto.EpisodeDTO;
import com.hcmute.myanime.dto.StatisticsEpisodeDTO;
import com.hcmute.myanime.exception.BadRequestException;
import com.hcmute.myanime.model.EpisodeEntity;
import com.hcmute.myanime.model.MovieSeriesEntity;
import com.hcmute.myanime.model.ViewStatisticsEntity;
import com.hcmute.myanime.repository.EpisodeRepository;
import com.hcmute.myanime.repository.MovieSeriesRepository;
import com.hcmute.myanime.repository.ViewStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class EpisodeService {

    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private MovieSeriesRepository movieSeriesRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private ViewStatisticsRepository viewStatisticsRepository;

    public List<EpisodeEntity> findBySeriesId(int seriesId){
        if(!movieSeriesRepository.findById(seriesId).isPresent()){
            throw new BadRequestException("Series not found");
        }
        List<EpisodeEntity> episodeEntityList = episodeRepository.findBySeriesId(seriesId);
        return episodeEntityList;
    }

    public EpisodeEntity findById(int episodeId) {
        Optional<EpisodeEntity> episodeEntityOptional = episodeRepository.findById(episodeId);
        if(!episodeEntityOptional.isPresent()) {
            throw new BadRequestException("Episode not found");
        }
        return episodeEntityOptional.get();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(EpisodeDTO episodeDTO, MultipartFile sourceFile, int seriesId) {
        Optional<MovieSeriesEntity> movieSeriesEntityOptional = movieSeriesRepository.findById(seriesId);
        if(!movieSeriesEntityOptional.isPresent()) {
            throw new BadRequestException("Series id not found");
        }
        MovieSeriesEntity movieSeriesEntity = movieSeriesEntityOptional.get();
        EpisodeEntity newEpisodeEntity = new EpisodeEntity();
        newEpisodeEntity.setMovieSeriesBySeriesId(movieSeriesEntity);
        newEpisodeEntity.setTitle(episodeDTO.getTitle());
        try {
            EpisodeEntity savedEntity = episodeRepository.save(newEpisodeEntity);
            String urlSource = uploadSourceFileToCloudinary(sourceFile, savedEntity.getId());
            if(!urlSource.equals("-1")) {
                savedEntity.setResource(urlSource);
                String resourcePublicId = "MyAnimeProject_TLCN" + "/" + "episode" + "/" + savedEntity.getId();
                savedEntity.setResourcePublicId(resourcePublicId);
                episodeRepository.save(savedEntity);
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String uploadSourceFileToCloudinary(MultipartFile sourceFile, int episodeId) {
        String urlSource = cloudinaryService.uploadFile(
                sourceFile,
                String.valueOf(episodeId),
                "MyAnimeProject_TLCN" + "/" + "episode");
        return urlSource;
    }


    public boolean updateByEpisodeId(int episodeId, EpisodeDTO episodeDTO, MultipartFile sourceFile) {
        Optional<EpisodeEntity> episodeEntityOptional = episodeRepository.findById(episodeId);
        if(!episodeEntityOptional.isPresent()) {
            throw new BadRequestException("Episode id not found");
        }
        EpisodeEntity updateEpisodeEntity = episodeEntityOptional.get();
        updateEpisodeEntity.setTitle(episodeDTO.getTitle());
        try {
            String urlSource = uploadSourceFileToCloudinary(sourceFile, updateEpisodeEntity.getId());
            if(!urlSource.equals("-1")) {
                updateEpisodeEntity.setResource(urlSource);
                String resourcePublicId = "MyAnimeProject_TLCN" + "/" + "episode" + "/" + updateEpisodeEntity.getId();
                updateEpisodeEntity.setResourcePublicId(resourcePublicId);
                episodeRepository.save(updateEpisodeEntity);
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    public boolean deleteById(int episodeId) {
        Optional<EpisodeEntity> episodeEntityOptional = episodeRepository.findById(episodeId);
        if(!episodeEntityOptional.isPresent()) {
            return false;
        }
        EpisodeEntity episodeEntity = episodeEntityOptional.get();
        if(episodeEntity.getResourcePublicId() != null) {
            cloudinaryService.deleteFileVideo(episodeEntity.getResourcePublicId());
        }
        try {
            episodeRepository.deleteById(episodeId);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean increaseView(int episodeId, String ipClient)
    {
        Optional<EpisodeEntity> episodeEntityOptional = episodeRepository.findById(episodeId);
        if(!episodeEntityOptional.isPresent())
            return false;

        EpisodeEntity episodeEntity = episodeEntityOptional.get();

        List<ViewStatisticsEntity> viewStatisticsEntityList = viewStatisticsRepository.findByIpAddressAndEpisode(ipClient, episodeEntity, PageRequest.of(0, 1));

        viewStatisticsEntityList.forEach((v)->{
            System.out.println(v.getCreateAt().getTime()/1000);
        });

        if(viewStatisticsEntityList.size() == 0)
        {
            ViewStatisticsEntity viewStatisticsEntityInsert = new ViewStatisticsEntity();
            viewStatisticsEntityInsert.setEpisode(episodeEntity);
            viewStatisticsEntityInsert.setIpAddress(ipClient);
            viewStatisticsRepository.save(viewStatisticsEntityInsert);
            episodeEntity.setTotalView(episodeEntity.getTotalView() + 1);
            episodeRepository.save(episodeEntity);
            return true;
        }

        ViewStatisticsEntity viewStatisticsEntity = viewStatisticsEntityList.get(0);
        Timestamp lastTimeView = viewStatisticsEntity.getCreateAt();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        long distanceSecond = (currentTimestamp.getTime() / 1000) - (lastTimeView.getTime() / 1000);

        // The rule is that each view of a client can only be increased at least once every 30 minutes
        if(distanceSecond < 1800)
            return false;

        ViewStatisticsEntity viewStatisticsEntityInsert = new ViewStatisticsEntity();
        viewStatisticsEntityInsert.setEpisode(episodeEntity);
        viewStatisticsEntityInsert.setIpAddress(ipClient);
        viewStatisticsRepository.save(viewStatisticsEntityInsert);
        episodeEntity.setTotalView(episodeEntity.getTotalView() + 1);
        episodeRepository.save(episodeEntity);
        return true;
    }

    public List<StatisticsEpisodeDTO> getTopMostView(int numberOfDay)
    {
        List<Object[]> listObject = viewStatisticsRepository.findTopMostViewWithDay(numberOfDay, PageRequest.of(0, 5));
        if(listObject.size() == 0)
            return null;

        List<StatisticsEpisodeDTO> statisticsEpisodeDTOList = new ArrayList<>();

        listObject.forEach((itemObj)->{
            EpisodeEntity episodeEntity = (EpisodeEntity) itemObj[0];
            long totalView = (Long) itemObj[1];
            if(episodeEntity != null)
            {
                StatisticsEpisodeDTO statisticsEpisodeDTO = new StatisticsEpisodeDTO(episodeEntity.getId(), episodeEntity.getCreateAt(), episodeEntity.getResource(), episodeEntity.getTitle(), totalView);
                statisticsEpisodeDTOList.add(statisticsEpisodeDTO);
            }
        });

        return statisticsEpisodeDTOList;
    }

}
