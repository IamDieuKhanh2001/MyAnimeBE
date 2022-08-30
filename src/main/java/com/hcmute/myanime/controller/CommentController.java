package com.hcmute.myanime.controller;

import com.hcmute.myanime.dto.CommentUserDTO;
import com.hcmute.myanime.dto.ResponseDTO;
import com.hcmute.myanime.model.CommentEntity;
import com.hcmute.myanime.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping()
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/user/comment/episode/{episodeId}")
    public ResponseEntity<?> getAllCommentByEpisodeId(@PathVariable int episodeId){
        List<CommentEntity> commentEntityList = commentService.findByEpisodeId(episodeId);
        List<CommentUserDTO> commentUserDTOList = new ArrayList<>();
        commentEntityList.forEach((commentEntity -> {
            CommentUserDTO commentUserDTO = new CommentUserDTO(
                    commentEntity.getId(),
                    commentEntity.getContent(),
                    commentEntity.getCreateAt(),
                    commentEntity.getEpisodeByEpisodeId().getId(),
                    commentEntity.getUsersByUserId().getUsername(),
                    commentEntity.getUsersByUserId().getAvatar()
            );
            commentUserDTOList.add(commentUserDTO);
        }));
        return ResponseEntity.ok(commentUserDTOList);
    }

    @PostMapping("/user/comment")
    public ResponseEntity<?> createCommentForEpisode(@RequestBody CommentUserDTO commentUserDTO) {

        if(commentService.save(commentUserDTO))
        {
            return ResponseEntity.ok(
                    new ResponseDTO(
                            HttpStatus.OK, "add comment success")
            );
        }
        else
        {
            return ResponseEntity.badRequest().body("add comment fail");
        }
    }

    @DeleteMapping("/user/comment/{commentId}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable int commentId) {
        if(commentService.deleteById(commentId)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, "Delete comment success")
            );
        } else {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.BAD_REQUEST, "Detele comment Fail")
            );
        }
    }
}
