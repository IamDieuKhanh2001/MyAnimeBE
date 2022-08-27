package com.hcmute.myanime.service;

import com.hcmute.myanime.dto.CategoryDTO;
import com.hcmute.myanime.dto.MovieDTO;
import com.hcmute.myanime.dto.MovieSeriesDTO;
import com.hcmute.myanime.exception.BadRequestException;
import com.hcmute.myanime.mapper.MovieMapper;
import com.hcmute.myanime.mapper.MovieSeriesMapper;
import com.hcmute.myanime.model.CategoryEntity;
import com.hcmute.myanime.model.MovieEntity;
import com.hcmute.myanime.model.MovieSeriesEntity;
import com.hcmute.myanime.repository.MovieSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieSeriesService {
    @Autowired
    private MovieSeriesRepository movieSeriesRepository;


    public List<MovieSeriesEntity> findAll()
    {
        return movieSeriesRepository.findAll();
    }

    public boolean save(MovieSeriesDTO movieSeriesDTO)
    {
        MovieSeriesEntity movieSeriesEntity = MovieSeriesMapper.toEntity(movieSeriesDTO);
        try
        {
            movieSeriesRepository.save(movieSeriesEntity);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public boolean updateById(int seriesID, MovieSeriesDTO movieSeriesDTO) {
        Optional<MovieSeriesEntity> movieSeriesEntity = movieSeriesRepository.findById(seriesID);
        if(!movieSeriesEntity.isPresent()) {
            return false;
        }

        MovieSeriesEntity updateMovieSeriesEntity = movieSeriesEntity.get();
        updateMovieSeriesEntity.setDateAired(movieSeriesDTO.getDateAired());
        updateMovieSeriesEntity.setDescription(movieSeriesDTO.getDescription());
        updateMovieSeriesEntity.setImage(movieSeriesDTO.getImage());
        updateMovieSeriesEntity.setName(movieSeriesDTO.getName());
        updateMovieSeriesEntity.setTotalEpisode(movieSeriesDTO.getTotalEpisode());
        try {
            movieSeriesRepository.save(updateMovieSeriesEntity);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean deleteById(int seriesID) {
        try {
            movieSeriesRepository.deleteById(seriesID);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
