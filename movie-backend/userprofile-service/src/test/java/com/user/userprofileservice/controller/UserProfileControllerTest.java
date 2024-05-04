package com.user.userprofileservice.controller;

import com.user.userprofileservice.dto.UserProfileDto;
import com.user.userprofileservice.exception.ResourceAlreadyExistsException;
import com.user.userprofileservice.exception.ResourceNotFoundException;
import com.user.userprofileservice.filter.JwtService;
import com.user.userprofileservice.model.UserProfile;
import com.user.userprofileservice.service.UserProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(UserProfileController.class)
class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileService userProfileService;

    @MockBean
    private JwtService jwtService;

    @Test
    void testGetUserProfileById() throws Exception {
        String token = "Bearer <your_token_here>";
        String username = "testUser";
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUsername(username);
        userProfileDto.setEmail("test@example.com");

        when(jwtService.isTokenValid(token, username)).thenReturn(true);
        when(userProfileService.getUserProfileById(username)).thenReturn(userProfileDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/private/userProfile/getUserById/{username}", username)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@example.com"));
    }
    @Test
    void testGetUserProfileById_Unauthorized() throws Exception {
        String token = "Bearer <your_token_here>";
        String username = "testUser";
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUsername(username);
        userProfileDto.setEmail("test@example.com");

        when(jwtService.isTokenValid(token, username)).thenReturn(false);
        when(userProfileService.getUserProfileById(username)).thenReturn(userProfileDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/private/userProfile/getUserById/{username}", username)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
    @Test
    void testGetUserProfileById_notfound() throws Exception {
        String token = "Bearer <your_token_here>";
        String username = "testUser";
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUsername(username);
        userProfileDto.setEmail("test@example.com");

        when(jwtService.isTokenValid(token, username)).thenReturn(true);
        when(userProfileService.getUserProfileById(username)).thenThrow(new ResourceNotFoundException("not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/private/userProfile/getUserById/{username}", username)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testUpdateUserProfile() throws Exception {
        String token = "Bearer <your_token_here>";
        String username = "testUser";
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUsername(username);
        userProfileDto.setEmail("updated@example.com");

        when(jwtService.isTokenValid(token, username)).thenReturn(true);
        when(userProfileService.updateUserProfile(userProfileDto, username)).thenReturn(userProfileDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1.0/private/userProfile/update/{username}", username)
                        .header("Authorization", token)
                        .content("{\"username\":\"testUser\",\"email\":\"updated@example.com\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("updated@example.com"));
    }

    @Test
    void testUpdateUserProfile_Unauthorized() throws Exception {
        String token = "Bearer <your_token_here>";
        String username = "testUser";
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUsername(username);
        userProfileDto.setEmail("updated@example.com");

        when(jwtService.isTokenValid(token, username)).thenReturn(false);
        when(userProfileService.updateUserProfile(userProfileDto, username)).thenReturn(userProfileDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1.0/private/userProfile/update/{username}", username)
                        .header("Authorization", token)
                        .content("{\"username\":\"testUser\",\"email\":\"updated@example.com\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
