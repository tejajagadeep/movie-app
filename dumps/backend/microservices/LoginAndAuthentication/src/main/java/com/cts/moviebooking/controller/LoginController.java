package com.cts.moviebooking.controller;

import com.cts.moviebooking.dto.UserDetailsResponseDto;
import com.cts.moviebooking.exception.ExternalServiceException;
import com.cts.moviebooking.exception.InvalidCredentialsException;
import com.cts.moviebooking.kafka.UserDetailsRetrievedConsumer;
import com.cts.moviebooking.security.AuthenticationService;
import com.cts.moviebooking.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/api/v1/auth")
public class LoginController {

    private final LoginService loginService;
    private final AuthenticationService authenticationService;

    private final UserDetailsRetrievedConsumer userDetailsRetrievedConsumer;

    @Autowired
    public LoginController(LoginService loginService, AuthenticationService authenticationService,UserDetailsRetrievedConsumer userDetailsRetrievedConsumer) {
        this.loginService = loginService;
        this.authenticationService = authenticationService;
        this.userDetailsRetrievedConsumer=userDetailsRetrievedConsumer;
    }

    @GetMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestParam String userName, @RequestParam String password, HttpServletResponse response) {
        try {
            ResponseEntity<?> userDetailsResponse = loginService.getLoginDetails(userName, password);

            if (userDetailsResponse.getBody() instanceof LinkedHashMap) {
                LinkedHashMap<?, ?> responseBody = (LinkedHashMap<?, ?>) userDetailsResponse.getBody();
                UserDetailsResponseDto userDetails = mapToUserDetails(responseBody);

                System.out.println("token from consumer string :-" + userDetailsRetrievedConsumer.getJwtToken());

                CompletableFuture<String> jwtTokenFuture = userDetailsRetrievedConsumer.consume(userDetails);

                try {
                    String jwtToken = jwtTokenFuture.get();
                    //userDetails.setToken(jwtToken);
                    System.out.println("in controller" + jwtToken);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                userDetails.setPassword(null);

                // Return a map as JSON response
                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("token", jwtTokenFuture.get());
                jsonResponse.put("userName", userDetails.getUserName());


                return ResponseEntity.status(userDetailsResponse.getStatusCodeValue()).body(jsonResponse);
            } else {
                return ResponseEntity.status(500).body("Unexpected response format");
            }
        } catch (InvalidCredentialsException e) {
            return handleException(HttpStatus.UNAUTHORIZED, "Invalid credentials", e);
        } catch (ExternalServiceException e) {
            return handleException(HttpStatus.INTERNAL_SERVER_ERROR, "Error calling user service please check the credentials", e);
        } catch (Exception e) {
            return handleException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    private ResponseEntity<String> handleException(HttpStatus status, String message, Exception e) {
        return ResponseEntity.status(status).body(message);
    }

    private UserDetailsResponseDto mapToUserDetails(LinkedHashMap<?, ?> responseBody) {
        UserDetailsResponseDto userDetails = new UserDetailsResponseDto();
        userDetails.setEmail((String) responseBody.get("email"));
        userDetails.setUserName((String) responseBody.get("userName"));
        userDetails.setFirstName((String) responseBody.get("firstName"));
        userDetails.setPassword((String) responseBody.get("password"));
        userDetails.setRole((String) responseBody.get("role"));
        return userDetails;
    }


    @GetMapping(path="/checkHi")
    public String sayHi()
    {
        return "Hi";
    }

    @GetMapping(path="/hello")
    public String sayHello()
    {
        return "Hello";
    }
}