package com.cts.moviebooking.security;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(authenticationService, "secretKey", "yourSecretKeyfnwejfbwebfwbewbewjbfwbfbwefbwefbwebfwebfjwfjwjwebbwebwbefwb");
        ReflectionTestUtils.setField(authenticationService, "expirationTime", "3600000");
    }

    @Test
    void testGenerateToken() {
        String userName = "testUser";
        String userEmail = "test@example.com";
        String role = "testRole";

        String generatedToken = authenticationService.generateToken(userName, userEmail,role);

        // Check if the token is not null
        assertNotNull(generatedToken);

        // You can add more assertions if needed based on your specific requirements
    }
}
