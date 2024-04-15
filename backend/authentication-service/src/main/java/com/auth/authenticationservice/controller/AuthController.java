package com.auth.authenticationservice.controller;

import com.auth.authenticationservice.filter.JwtService;
import com.auth.authenticationservice.service.AuthService;
import com.auth.authenticationservice.dto.AuthenticationRequest;
import com.auth.authenticationservice.dto.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
       return ResponseEntity.ok(authService.authenticate(request));
    }


    @PostMapping("/validate")
    @PreAuthorize("hasRole('MEMBER') && #username == authentication.principal.username")
    public Boolean post(@RequestHeader("Authorization") String token, @RequestParam String username) {
        return jwtService.isTokenValid(token, username);
    }
}
