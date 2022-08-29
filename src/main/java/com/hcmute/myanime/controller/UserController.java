package com.hcmute.myanime.controller;

import com.hcmute.myanime.auth.ApplicationUserService;
import com.hcmute.myanime.dto.ResponseDTO;
import com.hcmute.myanime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping()
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationUserService applicationUserService;

    @PostMapping("/user/avatar/upload")
    public ResponseEntity<?> uploadAvatar(@RequestParam(value = "avatar") MultipartFile avatar){
        if((!avatar.getContentType().equals("image/png") &&
                !avatar.getContentType().equals("image/jpeg")) || avatar.equals(null)) {
            return ResponseEntity.badRequest().body("file extension must be .jpeg or .png");
        }
        String username = applicationUserService.getUsernameLoggedIn();
        if(userService.uploadAvatar(avatar, username)) {
            return ResponseEntity.ok(
                    new ResponseDTO(
                    HttpStatus.OK,
                    "Update avatar user: " + username + " success"
                    )
            );
        } else {
            return ResponseEntity.badRequest().body("Update avatar user: " + username + " fail");
        }
    }
}
