package com.hcmute.myanime.mapper;

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
}
