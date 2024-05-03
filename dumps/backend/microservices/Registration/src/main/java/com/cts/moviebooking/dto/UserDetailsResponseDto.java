package com.cts.moviebooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponseDto implements Serializable {

    private String email;

    private String userName;

    private String firstName;

    private String password;

    private String role;

    public UserDetailsResponseDto(String email, String userName, String firstName, String password) {
        this.email = email;
        this.userName = userName;
        this.firstName = firstName;
        this.password = password;
    }
}

