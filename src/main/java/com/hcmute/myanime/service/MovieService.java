package com.hcmute.myanime.service;

import com.hcmute.myanime.dto.CategoryDTO;
import com.hcmute.myanime.dto.MovieDTO;
import com.hcmute.myanime.model.CategoryEntity;
import com.hcmute.myanime.model.MovieEntity;
import com.hcmute.myanime.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLStreamConstants;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    public List<MovieDTO> findAll()
    {
        List<MovieEntity> movieEntities = movieRepository.findAll();
        List<MovieDTO> movieDTO = new ArrayList<>();
        movieEntities.forEach((movieEntity) -> {
            MovieDTO movieDTO1 = new MovieDTO();
            movieDTO1.setId(movieEntity.getId());
            movieDTO1.setTitle(movieEntity.getTitle());

            movieDTO.add(movieDTO1);
        });
        return movieDTO;
    }

    public boolean save(MovieDTO movieDTO)
    {
        System.out.print(movieDTO.getTitle());
        MovieEntity movieEntity = new MovieEntity(
                movieDTO.getTitle(),
                new Date(System.currentTimeMillis()), // edit lai sau
                movieDTO.getDescription(),
                movieDTO.getVideoTrailer(),
                movieDTO.getStudioName(),
                movieDTO.getImage(),
                movieDTO.getDateAired()
        );
        try
        {
            movieRepository.save(movieEntity);
            return true;
        }
        catch (Exception ex)
        {
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
        movieEntity.setDescription(movieDTO.getDescription());
        movieEntity.setVideoTrailer(movieDTO.getVideoTrailer());
        movieEntity.setStudioName(movieDTO.getStudioName());
        movieEntity.setImage(movieDTO.getImage());
        movieEntity.setDateAired(movieDTO.getDateAired());

        try {
            movieRepository.save(movieEntity);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
