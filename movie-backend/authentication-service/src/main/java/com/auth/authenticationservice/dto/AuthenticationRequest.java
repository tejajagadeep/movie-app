package com.auth.authenticationservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticationRequest {

  @NotEmpty(message = "username is required field")
  private String username;
  @NotEmpty(message = "password is required field")
  private String password;
}
