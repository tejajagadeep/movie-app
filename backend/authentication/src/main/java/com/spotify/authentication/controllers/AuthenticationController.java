package com.spotify.authentication.controllers;

import com.spotify.authentication.ResponseHandler.CustomResponseHandler;
import com.spotify.authentication.exceptions.UserAlreadyExistsException;
import com.spotify.authentication.models.AuthRequest;
import com.spotify.authentication.models.AuthResponse;
import com.spotify.authentication.models.UserInfo;
import com.spotify.authentication.services.JwtService;
import com.spotify.authentication.services.UserInfoDetails;
import com.spotify.authentication.services.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/saveCredentials")
    public ResponseEntity<Object> addUserCredentials(@RequestBody UserInfo userInfo) throws UserAlreadyExistsException {
        log.info("addUserCredentials controller executed");
        return CustomResponseHandler.generateResponse("User Credentials added!", HttpStatus.OK, userInfoService.adduser(userInfo));
    }

    @PostMapping("/getToken")
    public ResponseEntity<Object> authenticateAndGetToken(@RequestBody AuthRequest authRequest)throws UsernameNotFoundException{
        log.info("authenticateAndGetToken controller executed!");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            log.warn("user authenticated!");
            return CustomResponseHandler.generateResponse("Token Generated!", HttpStatus.OK, jwtService.generateToken(authRequest.getUsername()));
        }
        else {
            log.error("UsernameNotFoundException occurred!!");
            throw new UsernameNotFoundException("Invalid User Credentials!");
        }
    }
    @GetMapping("/getCurrentLoggedInUser")
    public ResponseEntity<String> getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        return ResponseEntity.ok(authentication.getName());
    }
    @GetMapping("/validateToken")
    public ResponseEntity<Object> validate(@RequestHeader("Authorization") final String token){
        String token1 = token.substring(7);
        String username = jwtService.extractUsername(token1);
        AuthResponse authResponse = new AuthResponse();
        if(username!=null){
            UserDetails user = userInfoService.loadUserByUsername(username);
            if(jwtService.validateToken(token1, user)){
                authResponse.setValid(true);
                authResponse.setUsername(username);
            }
            else{
                authResponse.setValid(false);
            }
        }
        else {
            authResponse.setValid(false);
        }
        return CustomResponseHandler.generateResponse("Response generated", HttpStatus.OK, authResponse);
    }
}
