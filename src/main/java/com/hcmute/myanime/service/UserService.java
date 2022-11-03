package com.hcmute.myanime.service;

import com.hcmute.myanime.auth.ApplicationUserService;
import com.hcmute.myanime.common.GlobalVariable;
import com.hcmute.myanime.config.EmailTemplate;
import com.hcmute.myanime.dto.UserDTO;
import com.hcmute.myanime.exception.BadRequestException;
import com.hcmute.myanime.model.EmailConfirmationEntity;
import com.hcmute.myanime.model.UsersEntity;
import com.hcmute.myanime.repository.EmailConfirmationRepository;
import com.hcmute.myanime.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private ApplicationUserService applicationUserService;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private EmailConfirmationRepository emailConfirmationRepository;

    public List<UsersEntity> findAll() {
        return usersRepository.findAll();
    }

    public Boolean uploadAvatar(MultipartFile avatar, String username) {
        Optional<UsersEntity> usersOptional = usersRepository.findByUsername(username);
        System.out.println(username);
        if (!usersOptional.isPresent()) {
            return false;
        }
        UsersEntity userLogin = usersOptional.get();
        String url = cloudinaryService.uploadFile(
                avatar,
                String.valueOf(userLogin.getId()),
                "MyAnimeProject_TLCN/user/avatar");
        if (url.equals("-1")) {
            return false;
        }
        userLogin.setAvatar(url);
        try {
            usersRepository.save(userLogin);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public UsersEntity findUserLogging() {
        String usernameLoggedIn = applicationUserService.getUsernameLoggedIn();
        Optional<UsersEntity> userByUsername = usersRepository.findByUsername(usernameLoggedIn);
        if (!userByUsername.isPresent()) {
            throw new BadRequestException("User not found");
        }
        UsersEntity usersEntity = userByUsername.get();
        return usersEntity;
    }

    public boolean disableUserByUserId(int userId) {
        Optional<UsersEntity> usersEntityOptional = usersRepository.findById(userId);
        if (!usersEntityOptional.isPresent()) {
            return false;
        }
        UsersEntity userEntity = usersEntityOptional.get();
        userEntity.setEnable(false);
        try {
            usersRepository.save(userEntity);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean enableUserByUserId(int userId) {
        Optional<UsersEntity> usersEntityOptional = usersRepository.findById(userId);
        if (!usersEntityOptional.isPresent()) {
            return false;
        }
        UsersEntity userEntity = usersEntityOptional.get();
        userEntity.setEnable(true);
        try {
            usersRepository.save(userEntity);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public ResponseEntity<?> updateInfoUserLogging(UserDTO userDTO) {
        String usernameLoggedIn = applicationUserService.getUsernameLoggedIn();
        UsersEntity userEntity = usersRepository.findByUsername(usernameLoggedIn).get();
        String otpCode = GlobalVariable.GetOTP();
        Optional<UsersEntity> usersEntityOptional = usersRepository.findByEmail(userDTO.getEmail());
        if (usersEntityOptional.isPresent()) { //Check if mail has been used in other mail
            if(userEntity.getEmail() == null || !usersEntityOptional.get().equals(userEntity)) {
                return ResponseEntity.badRequest().body("Another account has used this email, please try another email");
            }
        }
        userEntity.setFullName(userDTO.getFullName());
        if(userEntity.getEmail() == null || !userEntity.getEmail().equals(userDTO.getEmail())) {
            System.out.println("change mail");
            try {
                sendRecoveryEmail(userDTO.getEmail(), usernameLoggedIn, otpCode);
                Timestamp expDate = new Timestamp(System.currentTimeMillis());
                expDate.setMinutes(expDate.getMinutes() + 10);
                Timestamp createAt = new Timestamp(System.currentTimeMillis());
                System.out.println(expDate);
                System.out.println(createAt);
                EmailConfirmationEntity emailConfirmationEntity = new
                        EmailConfirmationEntity(
                        otpCode,
                        GlobalVariable.EMAIL_CONFIRMATION_STATUS_PENDING,
                        userDTO.getEmail(),
                        "SetMail",
                        expDate,
                        createAt,
                        userEntity);
                emailConfirmationRepository.save(emailConfirmationEntity);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("This gmail is not valid");
            }
        }

        try {
            usersRepository.save(userEntity);
            return ResponseEntity.ok("Update user info success");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Update user fail");
        }
    }

    public void sendRecoveryEmail(String addressGmail, String username, String otpCode) throws MessagingException {
        emailSenderService.sendAsHTML(
                addressGmail,
                "[My anime corporation] You have request for adding new gmail for" + username,
                EmailTemplate.TemplateRecoveryPassword(username, otpCode)
        );

    }

    public ResponseEntity<?> checkUserMailOTPCode(String otpCode) {
        String usernameLoggedIn = applicationUserService.getUsernameLoggedIn();
        UsersEntity userEntity = usersRepository.findByUsername(usernameLoggedIn).get();
        return updateMailFromEmailConfirm(otpCode, userEntity);
    }

    @Transactional
    public ResponseEntity<?> updateMailFromEmailConfirm (String otpCode, UsersEntity userEntity) {
        Optional<EmailConfirmationEntity> emailConfirmationEntityOptional
                = emailConfirmationRepository.findByOtpCodeAndUsersEntityByUserId(otpCode, userEntity);
        if(emailConfirmationEntityOptional.isPresent()) {
            EmailConfirmationEntity userEmailConfirmEntity = emailConfirmationEntityOptional.get();
            Timestamp currentDate = new Timestamp(System.currentTimeMillis());
            if(userEmailConfirmEntity.getExpiredDate().after(currentDate)) {
                userEntity.setEmail(userEmailConfirmEntity.getEmail());
                try {
                    usersRepository.save(userEntity);
                    userEmailConfirmEntity.setStatus(GlobalVariable.EMAIL_CONFIRMATION_STATUS_USED);
                    emailConfirmationRepository.save(userEmailConfirmEntity);
                    return ResponseEntity.ok("Update user mail success");
                } catch (Exception ex) {
                    return ResponseEntity.badRequest().body("Update user mail fail");
                }
            } else {
                return ResponseEntity.badRequest().body("OTP code has been expired");
            }
        } else {
            return ResponseEntity.badRequest().body("OTP code not valid, try again");
        }
    }
}
