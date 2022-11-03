package com.hcmute.myanime.controller;

import com.hcmute.myanime.dto.GiftcodeDTO;
import com.hcmute.myanime.dto.ResponseDTO;
import com.hcmute.myanime.service.GiftcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("giftcode")
public class GiftcodeController {

    @Autowired
    private GiftcodeService giftcodeService;

    // User used giftcode
    @PostMapping("redeem")
    public ResponseEntity<?> redeem(@RequestBody GiftcodeDTO giftcodeDTO)
    {
        return null;
    }

    @PostMapping("create/package/{packageId}")
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

    @DeleteMapping("delete/{giftcodeId}")
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
}
