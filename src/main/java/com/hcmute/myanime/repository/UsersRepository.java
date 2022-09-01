package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {
    Optional<UsersEntity> findByUsername(String username);
}