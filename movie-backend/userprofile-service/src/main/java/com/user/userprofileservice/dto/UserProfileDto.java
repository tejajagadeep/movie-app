package com.user.userprofileservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {

        @NotEmpty(message = "username is required field")
        @Size(min = 5, max = 10, message = "username should be between 5 and 10 characters")
        private String username;

        @NotEmpty(message = "first name is required field")
        @Size(max = 100)
        private String firstName;

        @NotEmpty(message = "last name is required field")
        @Size(max = 100)
        private String lastName;

        @NotNull(message = "dateOfBirth is required field")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date dateOfBirth;

        @NotEmpty(message = "phone number is required field")
        @Pattern(
                regexp = "^\\d{10,}$",
                message = "phone number must contain 10 digits"
        )
        private String phoneNumber;

        @NotEmpty(message = "email is required field")
        @Size(max = 100)
        @Email(message = "enter valid email address")
        private String email;

        @NotEmpty(message = "Password is required field")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@^$#!%*?&()`~+=_-])[A-Za-z\\d@^$#!%*?&()`~+=_-]{8,}$",
                message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character"
        )
        @Size(max = 100)
        private String password;

}

