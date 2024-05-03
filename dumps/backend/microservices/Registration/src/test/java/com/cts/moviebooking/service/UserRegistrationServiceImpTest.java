package com.cts.moviebooking.service;

import com.cts.moviebooking.dto.UserDetailsRequestDto;
import com.cts.moviebooking.dto.UserDetailsResponseDto;
import com.cts.moviebooking.entity.UserEntity;
import com.cts.moviebooking.exception.InvalidPasswordException;
import com.cts.moviebooking.exception.UserNotFoundException;
import com.cts.moviebooking.repo.UserRepo;
import com.cts.moviebooking.serviceimpl.UserRegistrationServiceImp;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRegistrationServiceImpTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserRegistrationServiceImp registrationService;

    @Test
    public void testCreateUser() {
        // Given
        UserDetailsRequestDto userDetails = new UserDetailsRequestDto();
        userDetails.setUserName("testUser");
        userDetails.setEmail("test@example.com");
        userDetails.setPassword("testPassword");
        userDetails.setConfirmPassword("testPassword");

        UserEntity savedUser = new UserEntity();
        savedUser.setUserName("testUser");
        savedUser.setEmail("test@example.com");
        savedUser.setPassword("encodedPassword");

        when(bCryptPasswordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepo.save(any())).thenReturn(savedUser);

        // When
        UserDetailsResponseDto response = registrationService.createUser(userDetails);

        // Then
        verify(userRepo, Mockito.times(1)).save(any(UserEntity.class));
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("testUser", response.getUserName());
    }

    @Test
    public void testGetByUserNameAndPassword() {
        // Given
        UserEntity user = new UserEntity();
        user.setUserName("testUser");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userRepo.findByUserName("testUser")).thenReturn(user);
        when(bCryptPasswordEncoder.matches("testPassword", "encodedPassword")).thenReturn(true);

        // When
        UserDetailsResponseDto response = registrationService.getByUserNameAndPassword("testUser", "testPassword");

        // Then
        assertNotNull(response);
        assertEquals("testUser", response.getUserName());
        assertEquals("test@example.com", response.getEmail());
    }

    @Test
    public void testGetByUserNameAndPassword_UserNotFound() {
        // Given
        when(userRepo.findByUserName("nonExistentUser")).thenReturn(null);

        // When/Then
        assertThrows(UserNotFoundException.class, () -> registrationService.getByUserNameAndPassword("nonExistentUser", "testPassword"));
    }

    @Test
    public void testGetByUserNameAndPassword_InvalidPassword() {
        // Given
        UserEntity user = new UserEntity();
        user.setUserName("testUser");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userRepo.findByUserName("testUser")).thenReturn(user);
        when(bCryptPasswordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        // When/Then
        assertThrows(InvalidPasswordException.class, () -> registrationService.getByUserNameAndPassword("testUser", "wrongPassword"));
    }

    // Add more test cases as needed...
}
