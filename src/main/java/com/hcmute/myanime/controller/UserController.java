package com.hcmute.myanime.controller;

import com.hcmute.myanime.auth.ApplicationUserService;
import com.hcmute.myanime.dto.ResponseDTO;
import com.hcmute.myanime.dto.UserDTO;
import com.hcmute.myanime.mapper.UserMapper;
import com.hcmute.myanime.model.UsersEntity;
import com.hcmute.myanime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping()
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationUserService applicationUserService;

    @GetMapping("/admin/get-all-user")
    public ResponseEntity<?> getAllUSer() {
        List<UsersEntity> usersEntityList = userService.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        usersEntityList.forEach((usersEntity -> {
            userDTOList.add(UserMapper.toDto(usersEntity));
        }));
        return ResponseEntity.ok(userDTOList);
    }

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

    @GetMapping("/user/user-detail")
    public ResponseEntity<?> findUserLogging()
    {
        UsersEntity usersEntity = userService.findUserLogging();
        UserDTO userDtoLogging = UserMapper.toDto(usersEntity);
        return ResponseEntity.ok(userDtoLogging);
    }

    @PutMapping("/admin/disable-user/{userId}")
    public ResponseEntity<?> disableUserByUserId(@PathVariable int userId) {

        if(userService.disableUserByUserId(userId)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, "Disable user id " + userId + " success")
            );
        } else {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.BAD_REQUEST, "Disable user id " + userId + " fail")
            );
        }
    }

    @PutMapping("/admin/enable-user/{userId}")
    public ResponseEntity<?> enableUserByUserId(@PathVariable int userId) {
        if(userService.enableUserByUserId(userId)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, "Enable user id " + userId + " success")
            );
        } else {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.BAD_REQUEST, "Enable user id " + userId + " fail")
            );
        }
    }

//    @PutMapping("/user/user-detail")
//    public ResponseEntity<?> updateUserLogging(@RequestBody UserDTO userDTO) {
//
//        if(userService.updateUserLogging(userDTO)) {
//            return ResponseEntity.ok(
//                    new ResponseDTO(HttpStatus.OK, "Update user success")
//            );
//        } else {
//            return ResponseEntity.ok(
//                    new ResponseDTO(HttpStatus.BAD_REQUEST, "Update user fail")
//            );
//        }
//    }
}
