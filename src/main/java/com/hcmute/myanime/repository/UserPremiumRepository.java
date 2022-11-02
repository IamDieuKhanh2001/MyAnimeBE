package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.UserPremiumEntity;
import com.hcmute.myanime.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserPremiumRepository extends JpaRepository<UserPremiumEntity, Integer> {
    List<UserPremiumEntity> findByUsersEntityById(UsersEntity usersEntity);

    @Query("SELECT u FROM UserPremiumEntity u WHERE u.usersEntityById = :usersEntity AND u.expiredAt > current_timestamp")
    List<UserPremiumEntity> findByUserIdAndExpired(UsersEntity usersEntity);
}
