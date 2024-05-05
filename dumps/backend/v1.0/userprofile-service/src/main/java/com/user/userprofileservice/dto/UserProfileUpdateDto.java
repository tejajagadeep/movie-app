package com.user.userprofileservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileUpdateDto {

    @NotEmpty(message = "first name is required field")
    @Size(max = 100)
    private String firstName;

    @NotEmpty(message = "last name is required field")
    @Size(max = 100)
    private String lastName;

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

}
