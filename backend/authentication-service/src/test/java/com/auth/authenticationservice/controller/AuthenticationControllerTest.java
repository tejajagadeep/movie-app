package com.auth.authenticationservice.controller;

import com.auth.authenticationservice.dto.AuthAccessToken;
import com.auth.authenticationservice.dto.UserProfile;
import com.auth.authenticationservice.exception.CustomUnAuthorizedException;
import com.auth.authenticationservice.filter.JwtUtils;
import com.auth.authenticationservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPerformLoginSuccess() {
        String username = "testuser";
        String password = "testpassword";
        UserProfile userProfile = new UserProfile(username, password);

        AuthAccessToken authAccessToken = new AuthAccessToken();
        authAccessToken.setUsername(username);
        authAccessToken.setRole("User");
        authAccessToken.setMessage("User successfully logged in");
        authAccessToken.setJwtToken("testToken");

        when(userService.loginUser(username, password)).thenReturn(true);
        when(userService.getRoleByUserAndPass(username, password)).thenReturn("User");

        ResponseEntity<AuthAccessToken> response = authenticationController.performLogin(userProfile);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(authAccessToken, response.getBody());
    }

    @Test
    void testPerformLoginFailure() {
        String username = "testuser";
        String password = "testpassword";
        UserProfile userProfile = new UserProfile(username, password);

        when(userService.loginUser(username, password)).thenReturn(false);

        assertThrows(CustomUnAuthorizedException.class, () -> authenticationController.performLogin(userProfile));
    }

    @Test
    void testValidateTokenSuccess() {
        String token = "Bearer testToken";
        String username = "testuser";
        String role = "User";

        when(jwtUtils.validateJwtToken("testToken")).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken("testToken")).thenReturn(username);
        when(jwtUtils.getRoleFromToken("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYWdhZGVlcCIsInJvbGUiOiJVc2VyIiwiaWF0IjoxNzAyOTc2NjQ2fQ.fKutqrGMLjUnMRkZDc6uTY5RYodZc9nHDnkU0wnkVyw")).thenReturn(role);

        ResponseEntity<Object> response = authenticationController.validateToken(token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"testuser\":\"User\"}", response.getBody().toString());
    }

    @Test
    void testValidateTokenFailure() {
        String token = "InvalidToken";

        when(jwtUtils.validateJwtToken("InvalidToken")).thenReturn(false);

        assertThrows(CustomUnAuthorizedException.class, () -> authenticationController.validateToken(token));
    }
}
