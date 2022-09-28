package com.hcmute.myanime.repository;


import com.hcmute.myanime.model.EpisodeEntity;
import com.hcmute.myanime.model.ViewStatisticsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.*;

public interface ViewStatisticsRepository extends JpaRepository<ViewStatisticsEntity, Integer> {
    @Query(value = "SELECT v FROM ViewStatisticsEntity v WHERE v.ipAddress=:ipclient AND v.episode=:episode ORDER BY v.createAt DESC")
    List<ViewStatisticsEntity> findByIpAddressAndEpisode(String ipclient, EpisodeEntity episode, Pageable pageable);

    @Query(value = "SELECT v.episode, COUNT(v.id) AS statisticsView FROM ViewStatisticsEntity v WHERE DATEDIFF(current_timestamp, v.createAt) <= :numberOfDay GROUP BY v.episode ORDER BY statisticsView DESC")
    List<Object[]> findTopMostViewWithDay(int numberOfDay, Pageable pageable);

    Optional<ViewStatisticsEntity> findByIpAddress(String ipAddress);
    Optional<ViewStatisticsEntity> findByEpisode(EpisodeEntity episodeEntity);
}
