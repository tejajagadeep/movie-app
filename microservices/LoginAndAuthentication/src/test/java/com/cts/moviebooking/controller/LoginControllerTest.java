package com.cts.moviebooking.controller;

import com.cts.moviebooking.dto.UserDetailsResponseDto;
import com.cts.moviebooking.exception.ExternalServiceException;
import com.cts.moviebooking.exception.InvalidCredentialsException;
import com.cts.moviebooking.kafka.UserDetailsRetrievedConsumer;
import com.cts.moviebooking.security.AuthenticationService;
import com.cts.moviebooking.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LoginControllerTest {

    @Mock
    private LoginService loginService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserDetailsRetrievedConsumer userDetailsRetrievedConsumer;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginUser_SuccessfulLogin() {
        // Arrange
        String userName = "testUser";
        String password = "testPassword";
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        when(response.getHeader("token")).thenReturn("testJwtToken");
        when(response.getHeader("userName")).thenReturn(userName);

        when(loginService.getLoginDetails(Mockito.eq(userName), Mockito.eq(password)))
                .thenAnswer(invocation -> {
                    UserDetailsResponseDto userDetailsResponseDto = new UserDetailsResponseDto();
                    userDetailsResponseDto.setUserName(userName);
                    userDetailsResponseDto.setEmail("test@example.com");
                    userDetailsResponseDto.setFirstName("John");
                    userDetailsResponseDto.setPassword("hashedPassword");

                    // Convert UserDetailsResponseDto to LinkedHashMap
                    LinkedHashMap<String, Object> responseBody = mapUserDetailsToLinkedHashMap(userDetailsResponseDto);

                    return ResponseEntity.ok(responseBody);
                });

        CompletableFuture<String> jwtTokenFuture = CompletableFuture.completedFuture("testJwtToken");
        when(userDetailsRetrievedConsumer.consume(any(UserDetailsResponseDto.class))).thenReturn(jwtTokenFuture);

        // Act
        ResponseEntity<?> result = loginController.loginUser(userName, password, response);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }

    private LinkedHashMap<String, Object> mapUserDetailsToLinkedHashMap(UserDetailsResponseDto userDetails) {
        LinkedHashMap<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("userName", userDetails.getUserName());
        responseBody.put("email", userDetails.getEmail());
        responseBody.put("firstName", userDetails.getFirstName());
        // Add other fields as needed

        return responseBody;
    }


    @Test
    void testLoginUser_InvalidCredentials() {
        // Arrange
        String userName = "invalidUser";
        String password = "invalidPassword";
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        when(loginService.getLoginDetails(userName, password)).thenThrow(new InvalidCredentialsException("Invalid username or password"));

        // Act
        ResponseEntity<?> result = loginController.loginUser(userName, password, response);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        assertEquals("Invalid credentials", result.getBody());
    }

//    @Test
//    void testLoginUser_ExternalServiceError() {
//        // Arrange
//        String userName = "testUser";
//        String password = "testPassword";
//        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
//
//        when(loginService.getLoginDetails(userName, password)).thenThrow(new ExternalServiceException("Error calling user service"));
//
//        // Act
//        ResponseEntity<?> result = loginController.loginUser(userName, password, response);
//
//        // Assert
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
//        assertEquals("Error calling user service", result.getBody());
//    }
}
