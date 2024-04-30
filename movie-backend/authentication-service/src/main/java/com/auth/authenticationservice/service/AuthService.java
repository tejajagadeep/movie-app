package com.auth.authenticationservice.service;

import com.auth.authenticationservice.dto.AuthenticationRequest;
import com.auth.authenticationservice.dto.AuthenticationResponse;
import com.auth.authenticationservice.model.RegisterRequest;

public interface AuthService{
    void register(RegisterRequest registerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
