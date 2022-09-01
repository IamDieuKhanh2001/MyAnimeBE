package com.hcmute.myanime.repository;

import com.hcmute.myanime.model.CommentEntity;
import com.hcmute.myanime.model.EpisodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, Integer> {
    Long countByEpisodeByEpisodeId(EpisodeEntity episodeEntity);
}
