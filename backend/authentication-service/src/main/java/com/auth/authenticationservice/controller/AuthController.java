package com.auth.authenticationservice.controller;

import com.auth.authenticationservice.service.AuthService;
import com.auth.authenticationservice.dto.AuthenticationRequest;
import com.auth.authenticationservice.dto.AuthenticationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/public/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
       return ResponseEntity.ok(authService.authenticate(request));
    }


    @PostMapping("/validate")
    @PreAuthorize("hasRole('MEMBER') && #username == authentication.principal.username")
    public ResponseEntity<Object> validate(@RequestParam String username) {
        return new ResponseEntity<>(username+" Authorized", HttpStatus.ACCEPTED);
    }
}