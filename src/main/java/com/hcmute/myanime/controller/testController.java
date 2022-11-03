package com.hcmute.myanime.controller;

import com.hcmute.myanime.auth.ApplicationUserService;
import com.hcmute.myanime.service.CloudinaryService;
import com.hcmute.myanime.service.DigitalOceanSpaceService;
import com.hcmute.myanime.service.EmailSenderService;
import org.apache.commons.io.FilenameUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class testController {
    @Autowired
    private ApplicationUserService applicationUserService;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private DigitalOceanSpaceService digitalOceanSpaceService;

    @GetMapping("/test/api/do")
    public ResponseEntity<?> testDigitalOceanSpace() {

        return ResponseEntity.ok(digitalOceanSpaceService.getFileNames());
    }

    @PostMapping("/test/api/cd")
    public ResponseEntity<?> testCd(
            @RequestParam(value = "sourceFile", required = false) MultipartFile sourceFile) throws IOException {

        return ResponseEntity.ok(cloudinaryService.uploadFile(
                sourceFile.getBytes(),
                "test1",
                "MyAnimeProject_TLCN" + "/" + "test"));
    }
    @GetMapping("/test/api/asyns")
    public ResponseEntity<?> testAsyns(
            @RequestParam(value = "sourceFile", required = false) MultipartFile sourceFile
                                                   ) {
        digitalOceanSpaceService.sendTextMessage2(sourceFile);
        digitalOceanSpaceService.sendTextMessage();
        return ResponseEntity.ok("as");
    }
//    @PostMapping("/test/api/do")
//    public ResponseEntity<?> testDigitalOceanSpaceUploadFile(
//            @RequestParam(value = "sourceFile", required = false) MultipartFile sourceFile
//    ) throws IOException {
//        String url = digitalOceanSpaceService.uploadFile(
//                sourceFile,
//                "test4" + "." + FilenameUtils.getExtension(sourceFile.getOriginalFilename()),
//                "episode");
//        return ResponseEntity.ok(url);
//    }
    @GetMapping("/test/api")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Deployed 29/09/2022 Change Timezone Server USA");
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
    public ResponseEntity<?> upload(@RequestParam(value = "video", required = false) MultipartFile video) throws IOException {
        String url = cloudinaryService.uploadFile(
                video.getBytes(),
                "video2",
                "MyAnimeProject_TLCN" + "/" + "test");
        System.out.println(url);
        return ResponseEntity.ok("hello " + applicationUserService.getUsernameLoggedIn());
    }
    @GetMapping("/user/mail")
    public ResponseEntity<?> mail() {
        emailSenderService.sendSimpleEmail("quachdieukhanh@gmail.com","Test", "test body");
        return ResponseEntity.ok("hello");
    }


}