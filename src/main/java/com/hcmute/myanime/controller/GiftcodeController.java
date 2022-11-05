package com.hcmute.myanime.controller;

import com.hcmute.myanime.dto.GiftcodeDTO;
import com.hcmute.myanime.dto.ResponseDTO;
import com.hcmute.myanime.service.GiftcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GiftcodeController {

    @Autowired
    private GiftcodeService giftcodeService;

    //region Module Admin
    @PostMapping("admin/giftcode/create/package/{packageId}")
    public ResponseEntity<?> create(@RequestBody GiftcodeDTO giftcodeDTO, @PathVariable int packageId)
    {
        if(giftcodeService.save(giftcodeDTO, packageId)) {
            return ResponseEntity.ok(
                    new ResponseDTO(
                            HttpStatus.OK,
                            "Create new giftcode success"
                    )
            );
        } else {
            return ResponseEntity.badRequest().body("create giftcode fail");
        }
    }

    @DeleteMapping("admin/giftcode/delete/{giftcodeId}")
    public ResponseEntity<?> delete(@PathVariable int giftcodeId)
    {
        if(giftcodeService.destroy(giftcodeId)) {
            return ResponseEntity.ok(
                    new ResponseDTO(
                            HttpStatus.OK,
                            "Delete giftcode success"
                    )
            );
        } else {
            return ResponseEntity.badRequest().body("delete giftcode fail");
        }
    }
    //endregion

    //region Module Client

    // User used giftcode
    @PostMapping("user/giftcode/redeem")
    public ResponseEntity<?> redeem(@RequestBody GiftcodeDTO giftcodeDTO)
    {
        if(giftcodeService.redeem(giftcodeDTO))
            return ResponseEntity.ok(
                    new ResponseDTO(
                            HttpStatus.OK,
                            "Redeem giftcode success"
                    )
            );
        else {
            return ResponseEntity.badRequest().body("Redeem giftcode fail");
        }
    }
    //endregion


}
