package com.hcmute.myanime.service;

import com.hcmute.myanime.model.UsersEntity;
import com.hcmute.myanime.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

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
}
