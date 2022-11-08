package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.AttemptLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttemptLogRepository extends JpaRepository<AttemptLogEntity, Integer> {
    @Query("SELECT COUNT(a.id) AS total FROM AttemptLogEntity a WHERE a.ipAddress = :ipAddress AND a.attemptType = :attemptType AND TIMESTAMPADD(MINUTE, 10, a.createAt) > current_timestamp GROUP BY a.ipAddress")
    Optional<Object> getCountWithIpAndAttemptType(String ipAddress, String attemptType);

    @Modifying
    @Query("DELETE FROM AttemptLogEntity AS a WHERE a.ipAddress = :ipAddress AND a.attemptType = :attemptType")
    void deleteByIpAddressAndAttemptType(@Param("ipAddress") String ipAddress, @Param("attemptType") String attemptType);
}
