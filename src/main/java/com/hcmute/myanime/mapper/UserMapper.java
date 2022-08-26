package com.hcmute.myanime.mapper;

import com.hcmute.myanime.dto.UserDTO;
import com.hcmute.myanime.model.UsersEntity;

public class UserMapper {

    public static UsersEntity toEntity (UserDTO userDTO){
        UsersEntity mappedUserEntity = new UsersEntity();
        mappedUserEntity.setUsername(userDTO.getUsername());
        mappedUserEntity.setPassword(userDTO.getPassword());
        mappedUserEntity.setFullName(userDTO.getFullName());
        return mappedUserEntity;
    }
}
