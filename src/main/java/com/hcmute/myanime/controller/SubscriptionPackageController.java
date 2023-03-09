package com.hcmute.myanime.controller;

import com.hcmute.myanime.dto.ResponseDTO;
import com.hcmute.myanime.dto.StatisticsSubscriptionPackageDTO;
import com.hcmute.myanime.dto.SubcriptionPackageDTO;
import com.hcmute.myanime.service.SubscriptionPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SubscriptionPackageController{

    @Autowired
    private SubscriptionPackageService subscriptionPackageService;


    //region Module Admin
    @PostMapping("admin/subscription-package/create")
    public ResponseEntity<?> Create(@RequestBody SubcriptionPackageDTO subcriptionPackageDTO)
    {
        if(subscriptionPackageService.store(subcriptionPackageDTO)) {
            return ResponseEntity.ok(
                    new ResponseDTO(
                            HttpStatus.OK, "create subscription package success")
            );
        } else {
            return ResponseEntity.badRequest().body("create subscription package fail");
        }
    }

    @DeleteMapping("admin/subscription-package/delete/{packageID}")
    public ResponseEntity<?> Delete(@PathVariable int packageID)
    {
        if(subscriptionPackageService.destroy(packageID)) {
            return ResponseEntity.ok(
                    new ResponseDTO(
                            HttpStatus.OK, "Delete subscription package success")
            );
        } else {
            return ResponseEntity.badRequest().body("Delete subscription package fail");
        }
    }

    @PutMapping("admin/subscription-package/update/{packageID}")
    public ResponseEntity<?> Update(@RequestBody SubcriptionPackageDTO subcriptionPackageDTO, @PathVariable int packageID)
    {
        if(subscriptionPackageService.update(subcriptionPackageDTO, packageID)) {
            return ResponseEntity.ok(
                    new ResponseDTO(
                            HttpStatus.OK, "Update subscription package success")
            );
        } else {
            return ResponseEntity.badRequest().body("Update subscription package fail");
        }
    }
    //endregion

    //region Module Client
    @GetMapping("subscription-package")
    public ResponseEntity<?> GetAllPackage()
    {
        return ResponseEntity.ok(subscriptionPackageService.GetSubcriptionPackageActive());
    }
    @GetMapping("subscription-package/{packageID}")
    public ResponseEntity<?> countTopUpPackageByPackageID(@PathVariable int packageID,
                                                         @RequestParam Map<String, String> requestParams)
    {
        String paymentStatus = requestParams.get("paymentStatus");
        StatisticsSubscriptionPackageDTO statisticsSubscriptionPackageDTO = new StatisticsSubscriptionPackageDTO(
                subscriptionPackageService.countPackageByPackageIdAndStatus(packageID, paymentStatus)
        );
        return ResponseEntity.ok(
                statisticsSubscriptionPackageDTO
        );
    }
    //endregion

}
