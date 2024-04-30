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

        @Size(min = 5, max = 10, message = "username should be between 5 and 10 characters")
        private String username;

        @NotNull(message = "first name should not be Null")
        @NotEmpty(message = "first name should not be Empty")
        @Size(max = 100)
        private String firstName;

        @NotNull(message = "last name should not be Null")
        @NotEmpty(message = "last name should not be Empty")
        @Size(max = 100)
        private String lastName;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date dateOfBirth;

        @NotNull(message = "phone number should not be Null")
        @NotEmpty(message = "phone number should not be Empty")
        @Pattern(
                regexp = "^\\d{10,}$",
                message = "phone number must contain 10 digits"
        )
        private String phoneNumber;

        @NotNull(message = "emailAddress should not be Null")
        @NotEmpty(message = "emailAddress should not be Empty")
        @Size(max = 100)
        @Email(message = "enter valid email address")
        private String email;

        @NotNull(message = "password should not be Null")
        @NotEmpty(message = "password should not be Empty")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character"
        )
        @Size(max = 100)
        private String password;

}

