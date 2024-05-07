package com.auth.authenticationservice.service;

import com.auth.authenticationservice.dto.AuthenticationRequest;
import com.auth.authenticationservice.dto.AuthenticationResponse;
import com.auth.authenticationservice.exception.ResourceNotFoundException;
import com.auth.authenticationservice.exception.UnAuthorizedException;
import com.auth.authenticationservice.filter.JwtService;
import com.auth.authenticationservice.model.RegisterRequest;
import com.auth.authenticationservice.repository.UserRepository;
import com.auth.authenticationservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    private  final UserRepository userRepository;
    private  final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public void register(RegisterRequest registerRequest) {
        var user = User.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .role(registerRequest.getRole())
                .build();
        userRepository.save(user);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        //FirstStep
            //We need to validate our request (validate whether password & username is correct)
            //Verify whether user present in the database
            //Which AuthenticationProvider -> DaoAuthenticationProvider (Inject)
            //We need to authenticate using authenticationManager injecting this authenticationProvider
        //SecondStep
            //Verify whether userName and password is correct => UserNamePasswordAuthenticationToken
            //Verify whether user present in db
            //generateToken
            //Return the token
        var user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Username not exist."));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder().accessToken(jwtToken).build();
        } catch (AuthenticationException e) {
        // Throw UnAuthorizedException if authentication fails
        throw new UnAuthorizedException("Invalid Password");
    }
}
}

