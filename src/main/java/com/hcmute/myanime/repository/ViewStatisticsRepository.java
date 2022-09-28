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

    @Query(value = "SELECT v.episode, COUNT(v.id) AS totalView FROM ViewStatisticsEntity v WHERE DATEDIFF(current_timestamp, v.createAt) <= :day GROUP BY v.episode ORDER BY totalView DESC")
    List<Object[]> test(int day);

    @Query(value = "SELECT current_timestamp FROM ViewStatisticsEntity v WHERE v.id = 22")
    Optional<Objects> a();

    @Query(value = "SELECT v.createAt FROM ViewStatisticsEntity v WHERE v.id = 22")
    Optional<Objects> ab();

    @Query(value = "SELECT DATEDIFF(current_timestamp, v.createAt) FROM ViewStatisticsEntity v WHERE v.id = 22")
    Optional<Objects> abc();

    Optional<ViewStatisticsEntity> findByIpAddress(String ipAddress);
    Optional<ViewStatisticsEntity> findByEpisode(EpisodeEntity episodeEntity);
}
