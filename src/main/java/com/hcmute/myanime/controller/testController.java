package com.hcmute.myanime.controller;

import com.hcmute.myanime.auth.ApplicationUserService;
import com.hcmute.myanime.dto.ResponseDTO;
import com.hcmute.myanime.dto.UserDTO;
import com.hcmute.myanime.exception.BadRequestException;
import com.hcmute.myanime.service.CloudinaryService;
import com.hcmute.myanime.service.DigitalOceanSpaceService;
import com.hcmute.myanime.service.EmailSenderService;
import org.apache.commons.io.FilenameUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;


@RestController
public class testController {
    @Autowired
    private DigitalOceanSpaceService digitalOceanSpaceService;
    //Write your demo function here
    @RequestMapping(value = "/test/api/fileDel")
    public ResponseEntity<?> testdelFileDO() {
        boolean b = digitalOceanSpaceService.deleteFileVideo("episode/181.mp4");
        System.out.println(b);
        return ResponseEntity.badRequest().body("Del file success");
    }
}