package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.EmailConfirmationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmationEntity, Integer> {
}
