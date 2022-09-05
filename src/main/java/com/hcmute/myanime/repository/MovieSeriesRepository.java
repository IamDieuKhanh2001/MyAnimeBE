package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.MovieEntity;
import com.hcmute.myanime.model.MovieSeriesEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieSeriesRepository extends JpaRepository<MovieSeriesEntity, Integer> {
    List<MovieSeriesEntity> findByNameContaining(String keyword, Pageable pageable);
    Long countByNameContaining(String keyword);
    List<MovieSeriesEntity> findByIdAndMovieByMovieId(int id, MovieEntity movieEntity);
}
