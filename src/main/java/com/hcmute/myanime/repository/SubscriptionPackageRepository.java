package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.SubscriptionPackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionPackageRepository extends JpaRepository<SubscriptionPackageEntity, Integer> {
    @Query("SELECT s FROM SubscriptionPackageEntity s WHERE s.enable = '1'")
    List<SubscriptionPackageEntity> findAllByEnableActive();

    //Function SQL
    //Function MySql
    @Query(value = "select countTopupPackage(:subscriptionPackageId, :paymentStatus)", nativeQuery = true)
    Integer countTopUpPackageByFunction(
            @Param("subscriptionPackageId") int packageId,
            @Param("paymentStatus") String paymentStatus
    );
}
