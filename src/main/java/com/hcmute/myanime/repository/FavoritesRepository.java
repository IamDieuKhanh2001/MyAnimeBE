package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.FavoritesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesRepository extends JpaRepository<FavoritesEntity, Integer> {
}
