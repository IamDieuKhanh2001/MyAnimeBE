package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.SubscriptionPackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionPackageRepository extends JpaRepository<SubscriptionPackageEntity, Integer> {
    @Query("SELECT s FROM SubscriptionPackageEntity s WHERE s.enable = '1'")
    List<SubscriptionPackageEntity> findAllByEnableActive();
}
