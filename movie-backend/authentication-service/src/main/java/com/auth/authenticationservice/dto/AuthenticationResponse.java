package com.auth.authenticationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;
}
