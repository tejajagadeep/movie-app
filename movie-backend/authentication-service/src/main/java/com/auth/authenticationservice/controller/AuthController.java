package com.auth.authenticationservice.controller;

import com.auth.authenticationservice.exception.ErrorResponse;
import com.auth.authenticationservice.service.AuthService;
import com.auth.authenticationservice.dto.AuthenticationRequest;
import com.auth.authenticationservice.dto.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/public/auth")
@Log4j2
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Check User Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Access Token Created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "User not Found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) ) })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        log.info(request.toString());
        return new ResponseEntity<>(authService.authenticate(request), HttpStatus.CREATED);
    }


    @Operation(summary = "Check User Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "User Details Accepted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) ) })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/validate")
    @PreAuthorize("hasRole('MEMBER') && #username == authentication.principal.username")
    public ResponseEntity<Object> validate(@RequestParam String username) {
        log.info("username :{} validated", username);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
}
