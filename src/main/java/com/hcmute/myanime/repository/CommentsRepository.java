package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.CommentEntity;
import com.hcmute.myanime.model.EpisodeEntity;
import com.hcmute.myanime.model.MovieSeriesEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, Integer> {

    Long countByEpisodeByEpisodeId(EpisodeEntity episodeEntity);

//        @Query("SELECT c.episodeByEpisodeId.id FROM CommentEntity c ORDER BY c.createAt DESC")
//    List<Integer> getEpisodeIDCommentRecentWithLimit(Pageable pageable);
    @Query(value = "select distinct * from (select ms.id, ms.name, ms.description, ms.date_aired, ms.total_episode, ms.image, ms.create_at, ms.movie_id\n" +
            "                         from movie_series ms\n" +
            "                                  join episodes e on ms.id = e.series_id\n" +
            "                                  join comments c on e.id = c.episode_id\n" +
            "                         group by ms.id, c.create_at\n" +
            "                         order by c.create_at desc) as newT\n" +
            "LIMIT :limit", nativeQuery = true)
    List<Object[]> getEpisodeIDCommentRecentWithLimit(int limit);


//    @Query(value = "select ms from MovieSeriesEntity ms join " +
//            "ms.episodesById e join " +
//            "e.commentsById c group by ms.id, c.createAt order by c.createAt")
//    List<MovieSeriesEntity> getEpisodeIDCommentRecentWithLimit();
}