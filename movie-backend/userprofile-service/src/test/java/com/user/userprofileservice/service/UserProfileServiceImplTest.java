package com.user.userprofileservice.service;

import com.user.userprofileservice.dto.User;
import com.user.userprofileservice.dto.UserProfileDto;
import com.user.userprofileservice.dto.UserProfileUpdateDto;
import com.user.userprofileservice.exception.ResourceAlreadyExistsException;
import com.user.userprofileservice.exception.ResourceNotFoundException;
import com.user.userprofileservice.kafka.DataPublisherServiceImpl;
import com.user.userprofileservice.model.UserProfile;
import com.user.userprofileservice.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserProfileServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private DataPublisherServiceImpl producer;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserProfileById() {
        // Given
        String username = "testUser";
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(username);
        UserProfileDto expectedDto = new UserProfileDto();
        expectedDto.setUsername(username);
        when(userProfileRepository.findById(username)).thenReturn(Optional.of(userProfile));
        when(modelMapper.map(userProfile, UserProfileDto.class)).thenReturn(expectedDto);

        UserProfile result = userProfileService.getUserProfileById(username);

        assertEquals(expectedDto.getUsername(), result.getUsername());
    }
    @Test
    void testGetUserProfileById_notFount() {
        // Given
        String username = "testUser";
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(username);
        UserProfileDto expectedDto = new UserProfileDto();
        expectedDto.setUsername(username);
        when(userProfileRepository.findById(username)).thenReturn(Optional.empty());
        when(modelMapper.map(userProfile, UserProfileDto.class)).thenReturn(expectedDto);

        assertThrows(ResourceNotFoundException.class, ()->userProfileService.getUserProfileById(username));
    }

    @Test
    void saveUserProfile_UsernameExists_ExceptionThrown() {
        // Given
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUsername("existingUsername");

        when(userProfileRepository.existsById("existingUsername")).thenReturn(true);

        // Then
        assertThrows(ResourceAlreadyExistsException.class, () -> {
            userProfileService.saveUserProfile(userProfileDto);
        });

        // Ensure that save method is not called
        verify(userProfileRepository, never()).save(any(UserProfile.class));
    }

    @Test
    void saveUserProfile_EmailExists_ExceptionThrown() {
        // Given
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUsername("newUsername");
        userProfileDto.setEmail("existing@example.com");

        when(userProfileRepository.existsById("newUsername")).thenReturn(false);
        when(userProfileRepository.existsByEmail("existing@example.com")).thenReturn(true);

        // Then
        assertThrows(ResourceAlreadyExistsException.class, () -> {
            userProfileService.saveUserProfile(userProfileDto);
        });

        // Ensure that save method is not called
        verify(userProfileRepository, never()).save(any(UserProfile.class));
    }

    // Add more test cases to cover other scenarios
    @Test
    void testSaveUserProfile() {

        UserProfileDto userProfileDto = new UserProfileDto("testUser", "firstName", "lastName",  new Date(),"123456789", "email@email.com","ABCabc@123");
        User user = new User();
        user.setUsername(userProfileDto.getUsername());
        user.setPassword(userProfileDto.getPassword());
        UserProfile userProfile = new UserProfile("testUser", "firstName", "lastName", "123456789", new Date(), "email@email.com");

        when(modelMapper.map(userProfileDto, UserProfile.class)).thenReturn(new UserProfile());
        when(userProfileRepository.existsById(userProfileDto.getUsername())).thenReturn(false);
//        when(dataPublisherService.sendMessage(any(User.class))).thenReturn(mock(ListenableFuture.class));
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        UserProfile result = userProfileService.saveUserProfile(userProfileDto);
        System.out.println(result);
        verify(userProfileRepository, times(1)).save(any(UserProfile.class));
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void testUpdateUserProfile() {
        // Create a UserProfileUpdateDto
        UserProfileUpdateDto userProfileDto = new UserProfileUpdateDto("John","Doe","123456789","john.doe@example.com");

        // Mock behavior of UsersProfileRepository
        UserProfile existingUserProfile = new UserProfile();
        existingUserProfile.setEmail("old.email@example.com");
        when(userProfileRepository.findById("username")).thenReturn(java.util.Optional.of(existingUserProfile));
        when(userProfileRepository.existsByEmail("john.doe@example.com")).thenReturn(false);
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(existingUserProfile);
        // Invoke the method
        UserProfile updatedUserProfile = userProfileService.updateUserProfile(userProfileDto, "username");
        // Verify that save method is called with the updated user profile
        verify(userProfileRepository, times(1)).save(updatedUserProfile);

        // Assert that the returned user profile matches the updated data
        assertEquals("John", updatedUserProfile.getFirstName());
        assertEquals("Doe", updatedUserProfile.getLastName());
        assertEquals("john.doe@example.com", updatedUserProfile.getEmail());
        assertEquals("123456789", updatedUserProfile.getPhoneNumber());
    }
    @Test
    void updateUserProfile_UserProfileNotFound_ExceptionThrown() {
        // Given
        String username = "nonExistingUsername";
        UserProfileUpdateDto userProfileDto = new UserProfileUpdateDto();

        when(userProfileRepository.findById(username)).thenReturn(java.util.Optional.empty());

        // Then
        assertThrows(ResourceNotFoundException.class, () -> {
            userProfileService.updateUserProfile(userProfileDto, username);
        });

        verify(userProfileRepository, times(1)).findById(username);
        verify(userProfileRepository, never()).save(any(UserProfile.class));
    }

    @Test
    void updateUserProfile_EmailExists_ExceptionThrown() {
        // Given
        String username = "existingUsername";
        UserProfileUpdateDto userProfileDto = new UserProfileUpdateDto();
        userProfileDto.setEmail("existing@example.com");

        UserProfile existingUserProfile = new UserProfile();
        existingUserProfile.setUsername(username);
        existingUserProfile.setEmail("existing1@example.com");

        when(userProfileRepository.findById(username)).thenReturn(java.util.Optional.of(existingUserProfile));
        when(userProfileRepository.existsByEmail("existing@example.com")).thenReturn(true);

        // Then
        assertThrows(ResourceAlreadyExistsException.class, () -> {
            userProfileService.updateUserProfile(userProfileDto, username);
        });

        verify(userProfileRepository, times(1)).findById(username);
        verify(userProfileRepository, never()).save(any(UserProfile.class));
    }
}

