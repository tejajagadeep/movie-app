package com.user.userprofileservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.userprofileservice.dto.UserProfileDto;
import com.user.userprofileservice.exception.ResourceAlreadyExistsException;
import com.user.userprofileservice.model.UserProfile;
import com.user.userprofileservice.service.UserProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(PublicController.class)
class PublicControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileService userProfileService;

    private final UserProfileDto userProfileDto = new UserProfileDto();

    @BeforeEach
    void setContent(){

        userProfileDto.setUsername("jagadeep");
        userProfileDto.setFirstName("jagadeep");
        userProfileDto.setLastName("kollimarla");
        userProfileDto.setDateOfBirth(new Date()); // Adjust timezone if necessary
        userProfileDto.setPhoneNumber("7894561230");
        userProfileDto.setEmail("jagsg13ee11@gmail.com");
        userProfileDto.setPassword("ABCabc@123");
    }

    @Test
    void testSaveUserProfile() throws Exception {


        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userProfileDto);
        when(userProfileService.saveUserProfile(userProfileDto)).thenReturn(new UserProfile());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/public/userProfile/addUser", userProfileDto)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testSaveUserProfile_AlreadyExists() throws Exception {


        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userProfileDto);
        when(userProfileService.saveUserProfile(userProfileDto)).thenThrow(new ResourceAlreadyExistsException(""));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/public/userProfile/addUser", userProfileDto)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void testSaveUserProfile_badRequest() throws Exception {

        when(userProfileService.saveUserProfile(userProfileDto)).thenReturn(new UserProfile());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/public/userProfile/addUser", userProfileDto)
                        .content("{\"username\":\"testUser\",\"email\":\"updated@example.com\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testSaveUserProfile_badRequest_Username_Size() throws Exception {
        userProfileDto.setUsername("234");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userProfileDto);
        when(userProfileService.saveUserProfile(userProfileDto)).thenReturn(new UserProfile());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/public/userProfile/addUser", userProfileDto)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("username should be between 5 and 10 characters"))
        ;
    }

    @Test
    void testSaveUserProfile_badRequest_email() throws Exception {
        userProfileDto.setEmail(null);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userProfileDto);
        when(userProfileService.saveUserProfile(userProfileDto)).thenReturn(new UserProfile());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/public/userProfile/addUser", userProfileDto)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("email is required field"))
        ;
    }

    @Test
    void testSaveUserProfile_badRequest_email_valid() throws Exception {
        userProfileDto.setEmail("agdsa");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userProfileDto);
        when(userProfileService.saveUserProfile(userProfileDto)).thenReturn(new UserProfile());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/public/userProfile/addUser", userProfileDto)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("enter valid email address"))
        ;
    }

    @Test
    void testSaveUserProfile_badRequest_password() throws Exception {
        userProfileDto.setPassword("ABC");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userProfileDto);
        when(userProfileService.saveUserProfile(userProfileDto)).thenReturn(new UserProfile());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/public/userProfile/addUser", userProfileDto)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character"))
        ;
    }

    @Test
    void testSaveUserProfile_badRequest_PhoneNumber_containsDigits() throws Exception {
        userProfileDto.setPhoneNumber("swe213dgsdf34sssda");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userProfileDto);
        when(userProfileService.saveUserProfile(userProfileDto)).thenReturn(new UserProfile());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/public/userProfile/addUser", userProfileDto)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("phone number must contain 10 digits"))
        ;
    }


    @Test
    void testSaveUserProfile_badRequest_lastName_notEmpty() throws Exception {
        userProfileDto.setLastName("");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userProfileDto);
        when(userProfileService.saveUserProfile(userProfileDto)).thenReturn(new UserProfile());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/public/userProfile/addUser", userProfileDto)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("last name is required field"))
        ;
    }


    @Test
    void testSaveUserProfile_badRequest_notEmpty_firstname() throws Exception {
        userProfileDto.setFirstName("");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userProfileDto);
        when(userProfileService.saveUserProfile(userProfileDto)).thenReturn(new UserProfile());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/public/userProfile/addUser", userProfileDto)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("first name is required field"))
        ;
    }
}