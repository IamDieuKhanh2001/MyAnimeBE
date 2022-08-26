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
}
