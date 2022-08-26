package com.hcmute.myanime.controller;

import com.hcmute.myanime.auth.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class testController {
    @Autowired
    private ApplicationUserService applicationUserService;

    @GetMapping("/test/api")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("hello world");
    }
    @GetMapping("/test/api/adminRole")
    public ResponseEntity<?> testRole() {
        return ResponseEntity.ok("hello " + applicationUserService.GetUsernameLoggedIn());
    }
    @GetMapping("/test/api/userRole")
    public ResponseEntity<?> testRole2() {
        return ResponseEntity.ok("hello " + applicationUserService.GetUsernameLoggedIn());
    }
}
