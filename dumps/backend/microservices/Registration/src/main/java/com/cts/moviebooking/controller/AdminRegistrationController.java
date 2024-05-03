package com.cts.moviebooking.controller;

import com.cts.moviebooking.dto.BasicUserDetails;
import com.cts.moviebooking.dto.UserDetailsRequestDto;
import com.cts.moviebooking.dto.UserDetailsResponseDto;
import com.cts.moviebooking.exception.CustomErrorResponse;
import com.cts.moviebooking.exception.DuplicateEntryException;
import com.cts.moviebooking.exception.PasswordMismatchException;
import com.cts.moviebooking.repo.UserRepo;
import com.cts.moviebooking.service.AdminRegistrationService;
import com.cts.moviebooking.service.UserRegistrationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/adminprofile")
public class AdminRegistrationController {

    @Autowired
    private AdminRegistrationService registrationService;

    @Autowired
    private UserRepo userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AdminRegistrationController.class);



    @PostMapping(path = "/create",
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> createAdmin(@Valid @RequestBody UserDetailsRequestDto request) {
        try {
            // Check for duplicate username
            if (userRepository.existsByUserName(request.getUserName())) {
                throw new DuplicateEntryException("Username already exists");
            }

            // Check for duplicate email
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateEntryException("Email already exists");
            }

            // Password validation
            if (!request.getPassword().equals(request.getConfirmPassword())) {
                throw new PasswordMismatchException("Password and confirm password do not match");
            }

            UserDetailsResponseDto response = registrationService.createAdmin(request);
            logger.info("User created successfully: {}", response.getUserName());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DuplicateEntryException e) {
            logger.warn("Duplicate entry exception", e);
            return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()), HttpStatus.CONFLICT);
        } catch (PasswordMismatchException e) {
            logger.warn("Password mismatch exception", e);
            return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error", e);
            return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/allUsers")
    public ResponseEntity<List<BasicUserDetails>> getAllUserDetails() {
        List<BasicUserDetails> allUserDetails = registrationService.getAllUsers();
        return new ResponseEntity<>(allUserDetails, HttpStatus.OK);
    }


}
