package com.user.userprofileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {

        private String username;
        private String firstName;
        private String lastName;
        private Date dateOfBirth;
        private String email;
        private String password;

    }
