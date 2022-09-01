package com.hcmute.myanime.service;

import com.hcmute.myanime.dto.EpisodeDTO;
import com.hcmute.myanime.exception.BadRequestException;
import com.hcmute.myanime.model.EpisodeEntity;
import com.hcmute.myanime.model.MovieSeriesEntity;
import com.hcmute.myanime.repository.EpisodeRepository;
import com.hcmute.myanime.repository.MovieSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EpisodeService {

    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private MovieSeriesRepository movieSeriesRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    public List<EpisodeEntity> findBySeriesId(int seriesId){
        if(!movieSeriesRepository.findById(seriesId).isPresent()){
            throw new BadRequestException("Series not found");
        }
        List<EpisodeEntity> episodeEntityList = episodeRepository.findBySeriesId(seriesId);
        return episodeEntityList;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(EpisodeDTO episodeDTO, MultipartFile sourceFile, int seriesId) {
        Optional<MovieSeriesEntity> movieSeriesEntityOptional = movieSeriesRepository.findById(seriesId);
        if(!movieSeriesEntityOptional.isPresent()) {
            return false;
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
            return false;
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
}
