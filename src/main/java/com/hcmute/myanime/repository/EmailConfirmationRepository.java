package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.EmailConfirmationEntity;
import com.hcmute.myanime.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmationEntity, Integer> {
    Optional<EmailConfirmationEntity> findByOtpCodeAndUsersEntityByUserId(String otpCode, UsersEntity userEntity);
}
