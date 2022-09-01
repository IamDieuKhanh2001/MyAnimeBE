package com.hcmute.myanime.service;

import com.hcmute.myanime.auth.ApplicationUserService;
import com.hcmute.myanime.dto.UserDTO;
import com.hcmute.myanime.model.CategoryEntity;
import com.hcmute.myanime.model.UsersEntity;
import com.hcmute.myanime.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public List<UsersEntity> findAll() {
        return usersRepository.findAll();
    }

    public Boolean uploadAvatar(MultipartFile avatar, String username) {
        Optional<UsersEntity> usersOptional = usersRepository.findByUsername(username);
        System.out.println(username);
        if(!usersOptional.isPresent()) {
            return false;
        }
        UsersEntity userLogin = usersOptional.get();
        String url = cloudinaryService.uploadFile(
                avatar,
                String.valueOf(userLogin.getId()),
                "MyAnimeProject_TLCN/user/avatar");
        if(url.equals("-1")) {
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
        UsersEntity usersEntity = usersRepository.findByUsername(usernameLoggedIn).get();
        return usersEntity;
    }

    public boolean disableUserByUserId(int userId) {
        Optional<UsersEntity> usersEntityOptional = usersRepository.findById(userId);
        if(!usersEntityOptional.isPresent()) {
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
        if(!usersEntityOptional.isPresent()) {
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

//    public boolean updateUserLogging(UserDTO userDTO) {
//        String usernameLoggedIn = applicationUserService.getUsernameLoggedIn();
//        UsersEntity userEntity = usersRepository.findByUsername(usernameLoggedIn).get();
//
//        userEntity.set
//        try {
//            categoryRepository.save(updateCategoryEntity);
//            return true;
//        } catch (Exception ex) {
//            return false;
//        }
//    }
}
