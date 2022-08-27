package com.hcmute.myanime.service;

import com.cloudinary.api.exceptions.BadRequest;
import com.hcmute.myanime.exception.BadRequestException;
import com.hcmute.myanime.model.CategoryEntity;
import com.hcmute.myanime.model.EpisodeEntity;
import com.hcmute.myanime.model.MovieSeriesEntity;
import com.hcmute.myanime.repository.EpisodeRepository;
import com.hcmute.myanime.repository.MovieSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EpisodeService {

    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private MovieSeriesRepository movieSeriesRepository;

    public List<EpisodeEntity> findBySeriesId(int seriesId){
        if(!movieSeriesRepository.findById(seriesId).isPresent()){
            throw new BadRequestException("Series not found");
        }
        List<EpisodeEntity> episodeEntityList = episodeRepository.findBySeriesId(seriesId);
        return episodeEntityList;
    }

}
