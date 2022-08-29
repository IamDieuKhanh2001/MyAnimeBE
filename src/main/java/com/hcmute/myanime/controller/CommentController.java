package com.hcmute.myanime.controller;

import com.hcmute.myanime.dto.EpisodeDTO;
import com.hcmute.myanime.model.EpisodeEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping()
public class CommentController {

    @GetMapping(value = "/user/comment/episode/{episodeId}")
    public ResponseEntity<?> getAllCommentByEpisodeId(@PathVariable int episodeId){

        return ResponseEntity.ok("comment");
    }
}
