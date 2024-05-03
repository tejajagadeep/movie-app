package com.spotify.userprofile.service;

import com.spotify.userprofile.dto.UserProfileDto;
import com.spotify.userprofile.exception.ResourceAlreadyExistsException;
import com.spotify.userprofile.exception.ResourceNotFoundException;
import com.spotify.userprofile.model.UserProfile;
import com.spotify.userprofile.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserProfileServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        when(userProfileRepository.findAll()).thenReturn(Collections.singletonList(new UserProfile()));
        when(modelMapper.map(any(), eq(UserProfileDto.class))).thenReturn(new UserProfileDto());

        List<UserProfileDto> result = userProfileService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals(UserProfileDto.class, result.get(0).getClass());
    }

    @Test
    void testGetUserProfileById() {
        String username = "testUser";
        UserProfile userProfile = new UserProfile();
        when(userProfileRepository.findById(username)).thenReturn(Optional.of(userProfile));
        when(modelMapper.map(userProfile, UserProfileDto.class)).thenReturn(new UserProfileDto());

        UserProfileDto result = userProfileService.getUserProfileById(username);

        assertNotNull(result);
        assertEquals(UserProfileDto.class, result.getClass());
    }

    @Test
    void testGetUserProfileByIdNotFound() {
        String username = "nonexistentUser";
        when(userProfileRepository.findById(username)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userProfileService.getUserProfileById(username));
    }

    @Test
    void testSaveUserProfile() {
        UserProfile userProfile = new UserProfile();
        when(userProfileRepository.existsById(userProfile.getUsername())).thenReturn(false);
        when(userProfileRepository.existsByEmail(userProfile.getEmail())).thenReturn(false);
        when(userProfileRepository.save(userProfile)).thenReturn(userProfile);
        when(modelMapper.map(userProfile, UserProfileDto.class)).thenReturn(new UserProfileDto());

        UserProfileDto result = userProfileService.saveUserProfile(userProfile);

        assertNotNull(result);
        assertEquals(UserProfileDto.class, result.getClass());
    }

    @Test
    void testSaveUserProfileUsernameExists() {
        UserProfile userProfile = new UserProfile();
        when(userProfileRepository.existsById(userProfile.getUsername())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> userProfileService.saveUserProfile(userProfile));
    }

    @Test
    void testSaveUserProfileEmailExists() {
        UserProfile userProfile = new UserProfile();
        when(userProfileRepository.existsById(userProfile.getUsername())).thenReturn(false);
        when(userProfileRepository.existsByEmail(userProfile.getEmail())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> userProfileService.saveUserProfile(userProfile));
    }

    @Test
    void testUpdateUserProfile() {
        String username = "testUser";
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setFirstName("John");

        UserProfile userProfile = new UserProfile();
        when(userProfileRepository.findById(username)).thenReturn(Optional.of(userProfile));
        when(userProfileRepository.save(userProfile)).thenReturn(userProfile);
        when(modelMapper.map(userProfile, UserProfileDto.class)).thenReturn(userProfileDto);

        UserProfileDto result = userProfileService.updateUserProfile(userProfileDto, username);

        assertNotNull(result);
        assertEquals(UserProfileDto.class, result.getClass());
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testUpdateUserProfileNotFound() {
        String username = "nonexistentUser";
        UserProfileDto userProfileDto = new UserProfileDto();

        when(userProfileRepository.findById(username)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userProfileService.updateUserProfile(userProfileDto, username));
    }
}
