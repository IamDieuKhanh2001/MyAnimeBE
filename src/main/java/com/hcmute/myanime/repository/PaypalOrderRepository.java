package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.PaypalOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaypalOrderRepository extends JpaRepository<PaypalOrderEntity, Integer> {
    public Optional<PaypalOrderEntity> findByToken(String token);
}
