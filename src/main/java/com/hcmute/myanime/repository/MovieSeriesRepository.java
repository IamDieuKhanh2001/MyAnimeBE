package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.MovieSeriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieSeriesRepository extends JpaRepository<MovieSeriesEntity, Integer> {
}
