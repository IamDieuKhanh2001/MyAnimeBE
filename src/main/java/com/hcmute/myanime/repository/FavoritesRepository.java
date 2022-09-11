package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.CategoryEntity;
import com.hcmute.myanime.model.FavoritesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FavoritesRepository extends JpaRepository<FavoritesEntity, Integer> {
//    @Query( "DELETE FROM FavoritesEntity WHERE id = :ids" )
//    void deleteById(@Param("ids") Integer favoritesId);
}
