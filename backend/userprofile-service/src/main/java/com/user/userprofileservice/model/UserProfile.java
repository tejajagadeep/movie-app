package com.user.userprofileservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 5, max = 10, message = "userId should be between 5 and 10 characters")
    private String username;

    @NotNull(message = "name should not be Null")
    @NotEmpty(message = "name should not be Empty")
    @Size(max = 100)
    private String firstName;

    @NotNull(message = "name should not be Null")
    @NotEmpty(message = "name should not be Empty")
    @Size(max = 100)
    private String lastName;

    private Date dateOfBirth;

    @NotNull(message = "emailAddress should not be Null")
    @NotEmpty(message = "emailAddress should not be Empty")
    @Size(max = 100)
    @Email(message = "enter valid email address")
    private String email;

}
