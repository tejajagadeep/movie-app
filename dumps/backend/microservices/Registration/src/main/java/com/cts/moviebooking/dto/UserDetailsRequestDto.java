package com.cts.moviebooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsRequestDto {

    private String firstName;

    private String lastName;

    private String email;

    private String userName;

    private String password;

    private String confirmPassword;

}
