package com.cts.moviebooking.service;

import com.cts.moviebooking.dto.UserDetailsResponseDto;
import com.cts.moviebooking.exception.ExternalServiceException;
import com.cts.moviebooking.exception.InvalidCredentialsException;
import com.cts.moviebooking.feign.RegistrationClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LoginServiceImplTest {

    @Mock
    private RegistrationClient registrationClient;

    @InjectMocks
    private LoginServiceImpl loginService;

    //To avoid nullpointer exception
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getLoginDetails_ValidCredentials_ReturnsSuccessfulResponse() {

        String userName = "testUser";
        String password = "testPassword";

        UserDetailsResponseDto userDetailsResponseDto = new UserDetailsResponseDto();
        userDetailsResponseDto.setUserName(userName);
        userDetailsResponseDto.setEmail("test@example.com");
        userDetailsResponseDto.setFirstName("John");
        userDetailsResponseDto.setPassword("hashedPassword");

        when(registrationClient.getUserByUserNameAndPassword(userName, password))
                .thenAnswer(invocation -> ResponseEntity.ok(userDetailsResponseDto));

        ResponseEntity<?> result = loginService.getLoginDetails(userName, password);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getLoginDetails_InvalidCredentials_ThrowsInvalidCredentialsException() {
        when(registrationClient.getUserByUserNameAndPassword(any(), any()))
                .thenReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

        assertThrows(InvalidCredentialsException.class, () -> loginService.getLoginDetails("testUser", "testPassword"));
    }

    @Test
    void getLoginDetails_ExternalServiceError_ThrowsExternalServiceException() {
        when(registrationClient.getUserByUserNameAndPassword(any(), any()))
                .thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());

        assertThrows(ExternalServiceException.class, () -> loginService.getLoginDetails("testUser", "testPassword"));
    }

// Add more test cases for different scenarios

}
