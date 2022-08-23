package com.hcmute.myanime.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class testController {

    @GetMapping("/test/api")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("hello world");
    }
}
