package com.user.userprofileservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    @Id
    private String username;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Date dateOfBirth;

    private String email;

}
