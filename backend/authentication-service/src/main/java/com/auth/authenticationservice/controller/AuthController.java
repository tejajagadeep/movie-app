package com.auth.authenticationservice.controller;

import com.auth.authenticationservice.service.AuthService;
import com.auth.authenticationservice.dto.AuthenticationRequest;
import com.auth.authenticationservice.dto.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
        log.info(request.toString());
        return new ResponseEntity<>(authService.authenticate(request), HttpStatus.CREATED);
    }


    @Operation(summary = "Check User Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "User Details Accepted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden user mis-match",
                    content = @Content) })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/validate")
    @PreAuthorize("hasRole('MEMBER') && #username == authentication.principal.username")
    public ResponseEntity<Object> validate(@RequestParam String username) {
        return new ResponseEntity<>(username+" Authorized", HttpStatus.ACCEPTED);
    }
}
