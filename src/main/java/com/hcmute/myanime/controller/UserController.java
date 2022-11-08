package com.hcmute.myanime.controller;

import com.hcmute.myanime.auth.ApplicationUserService;
import com.hcmute.myanime.dto.ResponseDTO;
import com.hcmute.myanime.dto.UserDTO;
import com.hcmute.myanime.mapper.UserMapper;
import com.hcmute.myanime.model.UsersEntity;
import com.hcmute.myanime.service.EmailSenderService;
import com.hcmute.myanime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping()
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationUserService applicationUserService;


    //region Module Admin
    @GetMapping("/admin/get-all-user")
    public ResponseEntity<?> getAllUSer() {
        List<UsersEntity> usersEntityList = userService.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        usersEntityList.forEach((usersEntity -> {
            userDTOList.add(UserMapper.toDto(usersEntity));
        }));
        return ResponseEntity.ok(userDTOList);
    }
    @PutMapping("/admin/disable-user/{userId}")
    public ResponseEntity<?> disableUserByUserId(@PathVariable int userId) {

        if(userService.disableUserByUserId(userId)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, "Disable user id " + userId + " success")
            );
        } else {
            return ResponseEntity.badRequest().body("Disable user id " + userId + " fail");
        }
    }

    @PutMapping("/admin/enable-user/{userId}")
    public ResponseEntity<?> enableUserByUserId(@PathVariable int userId) {
        if(userService.enableUserByUserId(userId)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, "Enable user id " + userId + " success")
            );
        } else {
            return ResponseEntity.badRequest().body("Enable user id " + userId + " fail");
        }
    }
    //endregion

    //region Module Client
    @PostMapping("/user/avatar/upload")
    public ResponseEntity<?> uploadAvatar(@RequestParam(value = "avatar") MultipartFile avatar) throws IOException {
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
    @PutMapping("/user/user-detail")
    public ResponseEntity<?> updateInfoUserLogging(@RequestBody UserDTO userDTO) {
        ResponseEntity<?> responseEntity = userService.updateInfoUserLogging(userDTO);
        return responseEntity;
    }

    @GetMapping("/user/user-detail/mail/checkOTP/{otpCode}")
    public ResponseEntity<?> checkUserMailOTP(@PathVariable String otpCode) {
        ResponseEntity<?> responseEntity = userService.checkUserMailOTPCode(otpCode);
        return responseEntity;
    }

    // Premium member
    @GetMapping("user/user-detail/premium/check")
    public ResponseEntity<?> checkUserIsPremium()
    {
        if(userService.isPremiumMember()) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("user/user-detail/premium/remain")
    public ResponseEntity<?> checkRemainPremium()
    {
        return ResponseEntity.ok(userService.remainPremium());
    }

    @GetMapping("user/user-detail/premium/history")
    public ResponseEntity<?> getHistoryPremium()
    {
        return ResponseEntity.ok(userService.getHistoryPremium());
    }


    // Action này không được sử dụng bởi Client
    @PostMapping("user/user-detail/premium/package/{packageId}")
    public ResponseEntity<?> createPremium(@PathVariable int packageId)
    {
        if(userService.createPremium(packageId)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, "subcription premium success")
            );
        } else {
            return ResponseEntity.badRequest().body("subcription premium fail");
        }
    }

    @PostMapping("account/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestBody UserDTO userDTO)
    {
        StringBuilder message = new StringBuilder();

        if(userService.requestSendEmailForgetPassword(userDTO, message)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, message.toString())
            );
        } else {
            return ResponseEntity.badRequest().body(message.toString());
        }
    }

    @PostMapping("account/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, Object> request)
    {
        StringBuilder message = new StringBuilder();
        if(!request.containsKey("email")) {
            message.append("Key email missing");
            return ResponseEntity.badRequest().body(message.toString());
        } else if (!request.containsKey("code_confirmation")) {
            message.append("Key code_confirmation missing");
            return ResponseEntity.badRequest().body(message.toString());
        } else if (!request.containsKey("new_password")) {
            message.append("Key new_password missing");
            return ResponseEntity.badRequest().body(message.toString());
        }

        if(userService.requestResetPassword(request, message)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, message.toString())
            );
        } else {
            return ResponseEntity.badRequest().body(message.toString());
        }
    }

    //endregion
}
