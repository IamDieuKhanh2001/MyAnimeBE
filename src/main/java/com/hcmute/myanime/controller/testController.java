package com.hcmute.myanime.controller;

import com.hcmute.myanime.auth.ApplicationUserService;
import com.hcmute.myanime.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class testController {
    @Autowired
    private ApplicationUserService applicationUserService;
    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/test/api")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("hello world deploy 13/09/2022");
    }
    @GetMapping("/test/api/adminRole")
    public ResponseEntity<?> testRole() {
        return ResponseEntity.ok("hello " + applicationUserService.getUsernameLoggedIn());
    }
    @GetMapping("/test/api/userRole")
    public ResponseEntity<?> testRole2() {
        return ResponseEntity.ok("hello " + applicationUserService.getUsernameLoggedIn());
    }
    @PostMapping("/test/api/videoUpload")
    public ResponseEntity<?> upload(@RequestParam(value = "video", required = false) MultipartFile video) {
        String url = cloudinaryService.uploadFile(
                video,
                "video2",
                "MyAnimeProject_TLCN" + "/" + "test");
        System.out.println(url);
        return ResponseEntity.ok("hello " + applicationUserService.getUsernameLoggedIn());
    }
}
