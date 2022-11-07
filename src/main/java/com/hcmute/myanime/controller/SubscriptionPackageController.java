package com.hcmute.myanime.controller;

import com.hcmute.myanime.service.SubscriptionPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscription-package")
public class SubscriptionPackageController{

    @Autowired
    private SubscriptionPackageService subscriptionPackageService;

    @GetMapping("")
    public ResponseEntity<?> GetAllPackage()
    {
        return ResponseEntity.ok(subscriptionPackageService.GetSubcriptionPackageActive());
    }
}
