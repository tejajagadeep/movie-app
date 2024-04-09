package com.cts.moviebooking.controller;

import com.cts.moviebooking.dto.UserDetailsRequestDto;
import com.cts.moviebooking.dto.UserDetailsResponseDto;
import com.cts.moviebooking.exception.CustomErrorResponse;
import com.cts.moviebooking.exception.DuplicateEntryException;
import com.cts.moviebooking.exception.PasswordMismatchException;
import com.cts.moviebooking.exception.UserNotFoundException;
import com.cts.moviebooking.repo.UserRepo;
import com.cts.moviebooking.service.UserRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/userprofile")
public class UserRegistrationController {

    @Autowired
    private UserRegistrationService registrationService;

    @Autowired
    private KafkaTemplate<String, UserDetailsResponseDto> kafkaTemplate;

    @Autowired
    private UserRepo userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

    @GetMapping(path = "/hi")
    public String sayHi() {
        logger.info("Endpoint /hi accessed");
        return "Hi";
    }


    @PostMapping(path = "/create",
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDetailsRequestDto request) {
        try {
            System.out.println("Hiii");
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

            UserDetailsResponseDto response = registrationService.createUser(request);
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

    @GetMapping(path = "/getUser")
    public ResponseEntity<?> getUserByUserNameAndPassword(@RequestParam String userName, @RequestParam String password) {
        try {
            logger.info("Inside getUser");
            UserDetailsResponseDto response = registrationService.getByUserNameAndPassword(userName, password);
            logger.info("User details retrieved successfully: {}", response.getUserName());
            logger.info("User details retrieved successfully: {} in controller ",response.getRole());

           kafkaTemplate.send("user-details-retrieved-topic", response.getUserName(), response);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (UserNotFoundException e) {
            logger.warn("User not found exception", e);
            return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);

        } catch (PasswordMismatchException e) {
            logger.warn("Invalid password exception", e);
            return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error", e);
            return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
