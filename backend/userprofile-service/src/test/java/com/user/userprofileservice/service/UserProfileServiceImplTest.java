package com.user.userprofileservice.service;

import static org.junit.jupiter.api.Assertions.*;

import com.user.userprofileservice.dto.User;
import com.user.userprofileservice.dto.UserProfileDto;
import com.user.userprofileservice.exception.ResourceAlreadyExistsException;
import com.user.userprofileservice.kafka.DataPublisherServiceImpl;
import com.user.userprofileservice.model.UserProfile;
import com.user.userprofileservice.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private DataPublisherServiceImpl dataPublisherService;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

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

        UserProfileDto result = userProfileService.getUserProfileById(username);

        assertEquals(expectedDto, result);
    }

    @Test
    void testSaveUserProfile() {
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUsername("testUser");
        userProfileDto.setPassword("password");
        userProfileDto.setFirstName("firstName");
        userProfileDto.setLastName("lastName");
        userProfileDto.setEmail("email@email.com");
        User user = new User();
        user.setUsername(userProfileDto.getUsername());
        user.setPassword(userProfileDto.getPassword());
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername("testUser");
        userProfile.setFirstName("firstName");
        userProfile.setLastName("lastName");
        userProfile.setEmail("email@email.com");
        System.out.println(userProfile);
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
    void testSaveUserProfileWithExistingUsername() {
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUsername("existingUser");
        when(userProfileRepository.existsById(userProfileDto.getUsername())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> userProfileService.saveUserProfile(userProfileDto));
    }

}
