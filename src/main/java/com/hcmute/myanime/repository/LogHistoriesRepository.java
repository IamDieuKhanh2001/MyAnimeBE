package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.LogHistoriesEntity;
import com.hcmute.myanime.model.MovieSeriesEntity;
import com.hcmute.myanime.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LogHistoriesRepository extends JpaRepository<LogHistoriesEntity, Integer> {
    LogHistoriesEntity findByUserAndMovieSeriesEntity(UsersEntity usersEntity, MovieSeriesEntity movieSeriesEntity);
}
