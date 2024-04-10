package com.auth.authenticationservice.repository;

import com.auth.authenticationservice.model.UserDetails;
import com.auth.authenticationservice.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserRepoTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserRepo userRepoService; // Assuming a service is being used to interact with the repository

    @Test
    void testFindAll() {
        // Mocking the data returned by the repository
        UserDetails user1 = new UserDetails("user1", "password1", "ROLE_USER");
        UserDetails user2 = new UserDetails("user2", "password2", "ROLE_ADMIN");
        List<UserDetails> userList = Arrays.asList(user1, user2);

        // Mocking the behavior of the repository's findAll method
        when(userRepo.findAll()).thenReturn(userList);

        // Calling the service method that uses the repository
        List<UserDetails> result = userRepoService.findAll();

        // Verifying the expected behavior
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }

    // Additional test cases for other repository methods can be added as needed
}
