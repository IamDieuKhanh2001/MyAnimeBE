package com.hcmute.myanime.service;

import com.hcmute.myanime.auth.ApplicationUserService;
import com.hcmute.myanime.dto.CommentUserDTO;
import com.hcmute.myanime.model.CategoryEntity;
import com.hcmute.myanime.model.CommentEntity;
import com.hcmute.myanime.model.EpisodeEntity;
import com.hcmute.myanime.model.UsersEntity;
import com.hcmute.myanime.repository.CommentsRepository;
import com.hcmute.myanime.repository.EpisodeRepository;
import com.hcmute.myanime.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            System.out.println("aa");
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
            commentsRepository.deleteById(commentId);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
