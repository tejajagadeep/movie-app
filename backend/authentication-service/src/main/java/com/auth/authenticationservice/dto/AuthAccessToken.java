package com.auth.authenticationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthAccessToken {
    private String message;
    @JsonProperty("jwt_token")
    private String jwtToken;
    private String role;
    private String username;
}
