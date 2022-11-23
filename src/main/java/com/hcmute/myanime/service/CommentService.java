package com.hcmute.myanime.service;

import com.hcmute.myanime.auth.ApplicationUserService;
import com.hcmute.myanime.dto.CommentUserDTO;
import com.hcmute.myanime.dto.MovieSeriesDTO;
import com.hcmute.myanime.mapper.MovieSeriesMapper;
import com.hcmute.myanime.model.*;
import com.hcmute.myanime.repository.CommentsRepository;
import com.hcmute.myanime.repository.EpisodeRepository;
import com.hcmute.myanime.repository.MovieSeriesRepository;
import com.hcmute.myanime.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import java.sql.Timestamp;
import java.util.*;
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
    @Autowired
    private MovieSeriesRepository movieSeriesRepository;
    @Autowired
    private MovieSeriesService movieSeriesService;

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

    public List<MovieSeriesDTO> findSeriesCommentRecent(int limit)
    {
        List<Integer> listEpisodeCommentRecent = commentsRepository.getEpisodeIDCommentRecentWithLimit(PageRequest.of(0, 50));

        List<Integer> listIdEpTemp = new ArrayList<>();
        listEpisodeCommentRecent.forEach(id->{
            if(listIdEpTemp.size() >= limit)
                return;
            if(listIdEpTemp.contains(id))
                return;
            listIdEpTemp.add(id);
        });

        List<MovieSeriesDTO> movieSeriesDTOList = new ArrayList<>();
        listIdEpTemp.forEach(epID-> {
            Optional<EpisodeEntity> episodeEntityOptional = episodeRepository.findById(epID);
            if(episodeEntityOptional.isPresent()) {
                EpisodeEntity episodeEntity = episodeEntityOptional.get();
                MovieSeriesEntity movieSeriesEntity = episodeEntity.getMovieSeriesBySeriesId();
                Long viewOfSeries = movieSeriesService.totalViewByMovieSeriesEntity(movieSeriesEntity);
                movieSeriesDTOList.add(MovieSeriesMapper.toDTO(movieSeriesEntity, viewOfSeries));
            }
        });

        return movieSeriesDTOList;
    }
}
