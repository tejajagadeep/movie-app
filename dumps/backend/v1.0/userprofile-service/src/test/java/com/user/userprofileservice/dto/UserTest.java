package com.user.userprofileservice.dto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testNoArgsConstructor() {
        // Given
        User user = new User();

        // Then
        assertNotNull(user);
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        String username = "testUser";
        String password = "password123";
        String role = "admin";

        // When
        User user = new User(username, password, role);

        // Then
        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }

    @Test
    void testAccessors() {
        // Given
        User user = new User();

        // When
        user.setUsername("testUser");
        user.setPassword("password123");
        user.setRole("admin");

        // Then
        assertEquals("testUser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("admin", user.getRole());
    }
}
