package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.CommentEntity;
import com.hcmute.myanime.model.EpisodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, Integer> {
    Long countByEpisodeByEpisodeId(EpisodeEntity episodeEntity);
    @Query("SELECT c.episodeByEpisodeId.id FROM CommentEntity c ORDER BY c.createAt DESC")
    List<Integer> getEpisodeIDCommentRecentWithLimit(Pageable pageable);
}