package com.auth.authenticationservice.service;

import static org.junit.jupiter.api.Assertions.*;

import com.auth.authenticationservice.dto.AuthenticationRequest;
import com.auth.authenticationservice.dto.AuthenticationResponse;
import com.auth.authenticationservice.exception.ResourceNotFoundException;
import com.auth.authenticationservice.model.RegisterRequest;
import com.auth.authenticationservice.model.Role;
import com.auth.authenticationservice.repository.UserRepository;
import com.auth.authenticationservice.filter.JwtService;
import com.auth.authenticationservice.model.User;
import com.auth.authenticationservice.service.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void testRegister() {
        RegisterRequest registerRequest = new RegisterRequest("username", "password", Role.MEMBER);
        authService.register(registerRequest);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testAuthenticate() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("username", "password");
        User user = new User("username", "encodedPassword", Role.MEMBER);
        when(userRepository.findById("username")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("token");

        AuthenticationResponse response = authService.authenticate(authenticationRequest);

        assertEquals("token", response.getAccessToken());
    }

    @Test
    void testAuthenticateWithInvalidUser() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("nonExistingUsername", "password");
        when(userRepository.findById("nonExistingUsername")).thenReturn(Optional.empty());

        ResourceNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> authService.authenticate(authenticationRequest)
        );
        assertEquals("Username not exist.", exception.getMessage());
    }

}
