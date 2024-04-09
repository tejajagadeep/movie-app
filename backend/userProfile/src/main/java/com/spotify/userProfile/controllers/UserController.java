package com.spotify.userProfile.controllers;

import com.spotify.userProfile.Exceptions.UserAlreadyExistsException;
import com.spotify.userProfile.ResponseHandler.CustomResponseHandler;
import com.spotify.userProfile.models.User;
import com.spotify.userProfile.services.UserService;
import com.spotify.userProfile.services.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/userProfile")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User user) throws UserAlreadyExistsException {
        log.info("User registered!!");
        return CustomResponseHandler.generateResponse("User Created", HttpStatus.CREATED, userService.saveUser(user));
    }
}
