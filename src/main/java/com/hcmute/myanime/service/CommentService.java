package com.hcmute.myanime.service;

import com.hcmute.myanime.auth.ApplicationUserService;
import com.hcmute.myanime.dto.CommentUserDTO;
import com.hcmute.myanime.model.*;
import com.hcmute.myanime.repository.CommentsRepository;
import com.hcmute.myanime.repository.EpisodeRepository;
import com.hcmute.myanime.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CommentService {

    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ApplicationUserService applicationUserService;

    public List<CommentEntity> findByEpisodeId(int episodeId) {
        EpisodeEntity episodeEntity = episodeRepository.findById(episodeId).get();
        List<CommentEntity> commentEntityList = episodeEntity.getCommentsById().stream().toList();
        return commentEntityList;
    }

    public boolean save(CommentUserDTO commentUserDTO) {
        String usernameLoggedIn = applicationUserService.getUsernameLoggedIn();
        Optional<UsersEntity> userLoggedIn = usersRepository.findByUsername(usernameLoggedIn);
        Optional<EpisodeEntity> episodeEntityOptional = episodeRepository.findById(commentUserDTO.getEpisodeId());
        if(!episodeEntityOptional.isPresent() || !userLoggedIn.isPresent()) {
            return false;
        }
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentUserDTO.getContent());
        commentEntity.setEpisodeByEpisodeId(episodeEntityOptional.get());
        commentEntity.setUsersByUserId(userLoggedIn.get());
        try {
            commentsRepository.save(commentEntity);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean deleteById(int commentId) {
        try {
            String usernameLoggedIn = applicationUserService.getUsernameLoggedIn();
            Optional<UsersEntity> userLoggedIn = usersRepository.findByUsername(usernameLoggedIn);
            if(!userLoggedIn.isPresent())
                return false;
            UsersEntity usersEntity = userLoggedIn.get();

            Optional<CommentEntity> commentEntityOptional = commentsRepository.findById(commentId);
            if(!commentEntityOptional.isPresent())
                return false;
            CommentEntity commentEntity = commentEntityOptional.get();

            if(commentEntity.getUsersByUserId().getId() == usersEntity.getId())
            {
                commentsRepository.deleteById(commentId);
                return true;
            }
            return false;

        } catch (Exception ex) {
            return false;
        }
    }

    public Long totalCommentByEpisodeEntity(EpisodeEntity episodeEntity) {
        return commentsRepository.countByEpisodeByEpisodeId(episodeEntity);
    }

    public Long totalCommentByMovieSeriesEntity(MovieSeriesEntity movieSeriesEntity) {
        Long totalComment = Long.valueOf(0);
        for (EpisodeEntity item : movieSeriesEntity.getEpisodesById()) {
            totalComment += totalCommentByEpisodeEntity(item);
        }
        return totalComment;
    }
}
