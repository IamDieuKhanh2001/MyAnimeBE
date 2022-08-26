package com.hcmute.myanime.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hcmute.myanime.model.RolesEntity;

import java.sql.Timestamp;
import java.util.Set;

public class UserDTO {

    private String username;
    private String password;
    private String fullName;
    private String email;
    private String avatar;
    private RolesEntity rolesEntity;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public RolesEntity getRolesEntity() {
        return rolesEntity;
    }

    public void setRolesEntity(RolesEntity rolesEntity) {
        this.rolesEntity = rolesEntity;
    }
}
