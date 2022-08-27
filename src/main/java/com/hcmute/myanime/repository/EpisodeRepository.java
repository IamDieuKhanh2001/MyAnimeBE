package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.EpisodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodeRepository extends JpaRepository<EpisodeEntity, Integer> {
    @Query(value = "SELECT o FROM EpisodeEntity o WHERE o.movieSeriesBySeriesId.id=:seriesId")
    List<EpisodeEntity> findBySeriesId(int seriesId);
}
