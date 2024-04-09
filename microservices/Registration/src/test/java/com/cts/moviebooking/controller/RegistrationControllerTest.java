package com.cts.moviebooking.controller;

import com.cts.moviebooking.dto.UserDetailsRequestDto;
import com.cts.moviebooking.dto.UserDetailsResponseDto;
import com.cts.moviebooking.exception.CustomErrorResponse;
import com.cts.moviebooking.exception.PasswordMismatchException;
import com.cts.moviebooking.exception.UserNotFoundException;
import com.cts.moviebooking.repo.UserRepo;
import com.cts.moviebooking.service.UserRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegistrationControllerTest {

    @Mock
    private UserRegistrationService userRegistrationService;

    @Mock
    private KafkaTemplate<String, UserDetailsResponseDto> kafkaTemplate;

    @Mock
    private UserRepo userRepository;

    @InjectMocks
    private UserRegistrationController userRegistrationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        // Given
        UserDetailsRequestDto userDetails = new UserDetailsRequestDto();
        userDetails.setUserName("testUser");
        userDetails.setEmail("test@example.com");
        userDetails.setPassword("testPassword");
        userDetails.setConfirmPassword("testPassword");

        UserDetailsResponseDto responseDto = new UserDetailsResponseDto();
        responseDto.setUserName("testUser");
        responseDto.setEmail("test@example.com");
        responseDto.setPassword("encodedPassword");

        when(userRepository.existsByUserName(any())).thenReturn(false);
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRegistrationService.createUser(any())).thenReturn(responseDto);

        // When
        ResponseEntity<?> responseEntity = userRegistrationController.createUser(userDetails);

        // Then
        verify(userRegistrationService, times(1)).createUser(any(UserDetailsRequestDto.class));
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof UserDetailsResponseDto);
        UserDetailsResponseDto responseBody = (UserDetailsResponseDto) responseEntity.getBody();
        assertEquals("testUser", responseBody.getUserName());
    }

    @Test
    void testCreateUser_DuplicateEntryException() {
        // Given
        UserDetailsRequestDto userDetails = new UserDetailsRequestDto();
        userDetails.setUserName("existingUser");
        userDetails.setEmail("existing@example.com");
        userDetails.setPassword("testPassword");
        userDetails.setConfirmPassword("testPassword");

        when(userRepository.existsByUserName(any())).thenReturn(true);
        when(userRepository.existsByEmail(any())).thenReturn(false);

        // When
        ResponseEntity<?> responseEntity = userRegistrationController.createUser(userDetails);

        // Then
        verify(userRegistrationService, never()).createUser(any(UserDetailsRequestDto.class));
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof CustomErrorResponse);
        CustomErrorResponse errorResponse = (CustomErrorResponse) responseEntity.getBody();
        assertEquals(HttpStatus.CONFLICT.value(), errorResponse.getStatus());
        assertEquals("Username already exists", errorResponse.getMessage());
    }



    @Test
    void testGetUserByUserNameAndPassword_Success() {
        // Given
        when(userRegistrationService.getByUserNameAndPassword("testUser", "testPassword"))
                .thenReturn(new UserDetailsResponseDto("testName","testUser", "test@example.com", "encodedPassword"));

        // When
        ResponseEntity<?> responseEntity = userRegistrationController.getUserByUserNameAndPassword("testUser", "testPassword");

        // Then
        verify(userRegistrationService, times(1)).getByUserNameAndPassword("testUser", "testPassword");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof UserDetailsResponseDto);
        UserDetailsResponseDto responseBody = (UserDetailsResponseDto) responseEntity.getBody();
        assertEquals("testUser", responseBody.getUserName());
    }

    @Test
    void testGetUserByUserNameAndPassword_UserNotFoundException() {
        // Given
        when(userRegistrationService.getByUserNameAndPassword("nonExistentUser", "testPassword")).thenThrow(new UserNotFoundException("User not found"));

        // When
        ResponseEntity<?> responseEntity = userRegistrationController.getUserByUserNameAndPassword("nonExistentUser", "testPassword");

        // Then
        verify(userRegistrationService, times(1)).getByUserNameAndPassword("nonExistentUser", "testPassword");
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof CustomErrorResponse);
        CustomErrorResponse errorResponse = (CustomErrorResponse) responseEntity.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), errorResponse.getStatus());
        assertEquals("User not found", errorResponse.getMessage());
    }

    @Test
    void testGetUserByUserNameAndPassword_PasswordMismatchException() {
        // Given
        when(userRegistrationService.getByUserNameAndPassword("testUser", "wrongPassword")).thenThrow(new PasswordMismatchException("Invalid password"));

        // When
        ResponseEntity<?> responseEntity = userRegistrationController.getUserByUserNameAndPassword("testUser", "wrongPassword");

        // Then
        verify(userRegistrationService, times(1)).getByUserNameAndPassword("testUser", "wrongPassword");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof CustomErrorResponse);
        CustomErrorResponse errorResponse = (CustomErrorResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getStatus());
        assertEquals("Invalid password", errorResponse.getMessage());
    }

    @Test
    void testGetUserByUserNameAndPassword_InternalServerError() {
        // Given
        when(userRegistrationService.getByUserNameAndPassword("testUser", "testPassword")).thenThrow(new RuntimeException("Internal Server Error"));

        // When
        ResponseEntity<?> responseEntity = userRegistrationController.getUserByUserNameAndPassword("testUser", "testPassword");

        // Then
        verify(userRegistrationService, times(1)).getByUserNameAndPassword("testUser", "testPassword");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof CustomErrorResponse);
        CustomErrorResponse errorResponse = (CustomErrorResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorResponse.getStatus());

    }

}
