package com.hcmute.myanime.mapper;


import com.hcmute.myanime.dto.MovieDTO;
import com.hcmute.myanime.dto.MovieSeriesDTO;
import com.hcmute.myanime.model.MovieSeriesEntity;

public class MovieSeriesMapper {
    public static MovieSeriesEntity toEntity (MovieSeriesDTO movieSeriesDTO)
    {
        MovieSeriesEntity movieSeriesEntity = new MovieSeriesEntity();
        movieSeriesEntity.setId(movieSeriesDTO.getId());
        movieSeriesEntity.setCreateAt(movieSeriesDTO.getCreateAt());
        movieSeriesEntity.setDateAired(movieSeriesDTO.getDateAired());
        movieSeriesEntity.setDescription(movieSeriesDTO.getDescription());
        movieSeriesEntity.setImage(movieSeriesDTO.getImage());
        movieSeriesEntity.setName(movieSeriesDTO.getName());
        movieSeriesEntity.setTotalEpisode(movieSeriesDTO.getTotalEpisode());
        return movieSeriesEntity;
    }

    public static MovieSeriesDTO toDTO (MovieSeriesEntity movieSeriesEntity)
    {
        MovieSeriesDTO movieSeriesDTO = new MovieSeriesDTO();
        movieSeriesDTO.setId(movieSeriesEntity.getId());
        movieSeriesDTO.setCreateAt(movieSeriesEntity.getCreateAt());
        movieSeriesDTO.setDateAired(movieSeriesEntity.getDateAired());
        movieSeriesDTO.setDescription(movieSeriesEntity.getDescription());
        movieSeriesDTO.setImage(movieSeriesEntity.getImage());
        movieSeriesDTO.setName(movieSeriesEntity.getName());
        movieSeriesDTO.setTotalEpisode(movieSeriesEntity.getTotalEpisode());
        movieSeriesDTO.setMovieId(movieSeriesEntity.getMovieByMovieId().getId());
        movieSeriesDTO.setMovieData(MovieMapper.toDTO(movieSeriesEntity.getMovieByMovieId()));

        return movieSeriesDTO;
    }
}
