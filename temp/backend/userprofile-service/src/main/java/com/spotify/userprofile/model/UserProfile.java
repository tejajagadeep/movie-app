package com.spotify.userprofile.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String password;
    private Date dateOfBirth;
    @NotNull
    private String email;
    private String gender;


}
