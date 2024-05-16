package com.auth.authenticationservice.controller;

import com.auth.authenticationservice.dto.AuthenticationRequest;
import com.auth.authenticationservice.dto.AuthenticationResponse;
import com.auth.authenticationservice.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void testAuthenticate() {
        // Given
        AuthenticationRequest request = new AuthenticationRequest("username", "password");
        AuthenticationResponse response = new AuthenticationResponse("token");
        when(authService.authenticate(request)).thenReturn(response);

        ResponseEntity<AuthenticationResponse> result = authController.authenticate(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void testValidate() {
        String username = "testuser";

        ResponseEntity<Object> result = authController.validate(username);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        assertEquals(true, result.getBody());
    }
}
