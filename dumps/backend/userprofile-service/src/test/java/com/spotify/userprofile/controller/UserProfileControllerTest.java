package com.spotify.userprofile.controller;

import com.spotify.userprofile.dto.UserProfileDto;
import com.spotify.userprofile.exception.UnAuthorizedException;
import com.spotify.userprofile.kafka.DataPublisherServiceImpl;
import com.spotify.userprofile.model.UserProfile;
import com.spotify.userprofile.service.AuthService;
import com.spotify.userprofile.service.UserProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserProfileControllerTest {

    @Mock
    private UserProfileService userProfileService;

    @Mock
    private AuthService authService;

    @Mock
    private DataPublisherServiceImpl producer;

    @InjectMocks
    private UserProfileController userProfileController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        when(authService.validateToken(anyString())).thenReturn(Collections.singletonMap("Admin", "admin"));
        when(userProfileService.getAllUsers()).thenReturn(Collections.singletonList(new UserProfileDto()));

        ResponseEntity<Object> response = userProfileController.getAllUsers("Bearer token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(UserProfileDto.class, ((UserProfileDto) response.getBody()).getClass());
    }

    @Test
    void testGetAllUsersUnauthorized() {
        when(authService.validateToken(anyString())).thenReturn(Collections.emptyMap());

        assertThrows(UnAuthorizedException.class, () -> userProfileController.getAllUsers("Bearer token"));
    }

    @Test
    void testGetUserProfileById() {
        String username = "testUser";
        Map<String, String> tokenInfo = Collections.singletonMap(username, "User");
        when(authService.validateToken(anyString())).thenReturn(tokenInfo);
        when(userProfileService.getUserProfileById(username)).thenReturn(new UserProfileDto());

        ResponseEntity<Object> response = userProfileController.getUserProfileById("Bearer token", username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(UserProfileDto.class, ((UserProfileDto) response.getBody()).getClass());
    }

    @Test
    void testGetUserProfileByIdUnauthorized() {
        String username = "testUser";
        when(authService.validateToken(anyString())).thenReturn(Collections.emptyMap());

        assertThrows(UnAuthorizedException.class, () -> userProfileController.getUserProfileById("Bearer token", username));
    }

    @Test
    void testSaveUserProfile() {
        UserProfile userProfile = new UserProfile();
        UserProfileDto userProfileDto = new UserProfileDto();
        when(userProfileService.saveUserProfile(userProfile)).thenReturn(userProfileDto);

        ResponseEntity<Object> response = userProfileController.saveUserProfile(userProfile);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(UserProfileDto.class, ((UserProfileDto) response.getBody()).getClass());
    }

    @Test
    void testUpdateUserProfile() {
        String username = "testUser";
        UserProfileDto userProfileDto = new UserProfileDto();
        Map<String, String> tokenInfo = Collections.singletonMap(username, "User");
        when(authService.validateToken(anyString())).thenReturn(tokenInfo);
        when(userProfileService.updateUserProfile(userProfileDto, username)).thenReturn(userProfileDto);

        ResponseEntity<Object> response = userProfileController.updateUserProfile("Bearer token", userProfileDto, username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(UserProfileDto.class, ((UserProfileDto) response.getBody()).getClass());
    }

    @Test
    void testUpdateUserProfileUnauthorized() {
        String username = "testUser";
        UserProfileDto userProfileDto = new UserProfileDto();
        when(authService.validateToken(anyString())).thenReturn(Collections.emptyMap());

        assertThrows(UnAuthorizedException.class, () ->
                userProfileController.updateUserProfile("Bearer token", userProfileDto, username));
    }
}
