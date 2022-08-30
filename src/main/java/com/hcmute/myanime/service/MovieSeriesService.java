package com.hcmute.myanime.service;

import com.hcmute.myanime.dto.MovieSeriesDTO;
import com.hcmute.myanime.mapper.MovieSeriesMapper;
import com.hcmute.myanime.model.MovieEntity;
import com.hcmute.myanime.model.MovieSeriesEntity;
import com.hcmute.myanime.repository.MovieRepository;
import com.hcmute.myanime.repository.MovieSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class MovieSeriesService {
    @Autowired
    private MovieSeriesRepository movieSeriesRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CloudinaryService cloudinaryService;


    public List<MovieSeriesEntity> findAll()
    {
        return movieSeriesRepository.findAll();
    }

    public boolean save(MovieSeriesDTO movieSeriesDTO, MultipartFile sourceFile)
    {
        MovieSeriesEntity movieSeriesEntity = MovieSeriesMapper.toEntity(movieSeriesDTO);
        Optional<MovieEntity> movieEntityOptional = movieRepository.findById(movieSeriesDTO.getMovieId());
        if(!movieEntityOptional.isPresent()) {
            return false;
        }
        MovieEntity movieEntity = movieEntityOptional.get();
        movieSeriesEntity.setMovieByMovieId(movieEntity);
        try
        {
            MovieSeriesEntity savedEntity = movieSeriesRepository.save(movieSeriesEntity);
            String urlSource = uploadSourceFileToCloudinary(sourceFile, savedEntity.getId());
            if(!urlSource.equals("-1")) {
                savedEntity.setImage(urlSource);
                movieSeriesRepository.save(savedEntity);
            }
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public String uploadSourceFileToCloudinary(MultipartFile sourceFile, int seriesId) {
        String urlSource = cloudinaryService.uploadFile(
                sourceFile,
                String.valueOf(seriesId),
                "MyAnimeProject_TLCN" + "/" + "movie_series");
        return urlSource;
    }

    public boolean updateById(int seriesID, MovieSeriesDTO movieSeriesDTO, MultipartFile sourceFile) {
        Optional<MovieSeriesEntity> movieSeriesEntity = movieSeriesRepository.findById(seriesID);
        if(!movieSeriesEntity.isPresent()) {
            return false;
        }

        MovieSeriesEntity updateMovieSeriesEntity = movieSeriesEntity.get();
        updateMovieSeriesEntity.setDateAired(movieSeriesDTO.getDateAired());
        updateMovieSeriesEntity.setDescription(movieSeriesDTO.getDescription());
        updateMovieSeriesEntity.setName(movieSeriesDTO.getName());
        updateMovieSeriesEntity.setTotalEpisode(movieSeriesDTO.getTotalEpisode());
        try {
            MovieSeriesEntity savedEntity = movieSeriesRepository.save(updateMovieSeriesEntity);
            String urlSource = uploadSourceFileToCloudinary(sourceFile, savedEntity.getId());
            if(!urlSource.equals("-1")) {
                savedEntity.setImage(urlSource);
                movieSeriesRepository.save(savedEntity);
            }
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
