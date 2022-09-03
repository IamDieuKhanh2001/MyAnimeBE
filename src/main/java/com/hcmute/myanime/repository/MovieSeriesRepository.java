package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.MovieSeriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface MovieSeriesRepository extends JpaRepository<MovieSeriesEntity, Integer> {
//    List<MovieSeriesEntity> findAll(Pageable pageable);
}
