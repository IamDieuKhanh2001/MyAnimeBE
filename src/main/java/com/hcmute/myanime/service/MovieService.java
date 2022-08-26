package com.hcmute.myanime.service;

import com.hcmute.myanime.dto.MovieDTO;
import com.hcmute.myanime.mapper.MovieMapper;
import com.hcmute.myanime.model.MovieEntity;
import com.hcmute.myanime.repository.MovieRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    public List<MovieDTO> findAll()
    {
        List<MovieEntity> movieEntities = movieRepository.findAll();
        List<MovieDTO> movieDTO = new ArrayList<>();
        movieEntities.forEach((movieEntity) -> {
            MovieDTO movieDTO1 = MovieMapper.toDTO(movieEntity);
            movieDTO.add(movieDTO1);
        });
        return movieDTO;
    }

    public boolean save(MovieDTO movieDTO)
    {
        MovieEntity movieEntity = new MovieEntity(
                movieDTO.getTitle(),
                movieDTO.getStudioName()
        );
        try
        {
            MovieEntity movieEntitySaved = movieRepository.save(movieEntity);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(movieEntitySaved);
            System.out.println(json);
            return true;
        }
        catch (Exception ex)
        {
            System.out.print(ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(int movieId) {
        try {
            movieRepository.deleteById(movieId);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean updateById(int movieId, MovieDTO movieDTO) {
        Optional<MovieEntity> movieById = movieRepository.findById(movieId);
        if(!movieById.isPresent()) {
            return false;
        }
        MovieEntity movieEntity = movieById.get();
        movieEntity.setTitle(movieDTO.getTitle());
        movieEntity.setStudioName(movieDTO.getStudioName());
        try {
            movieRepository.save(movieEntity);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
