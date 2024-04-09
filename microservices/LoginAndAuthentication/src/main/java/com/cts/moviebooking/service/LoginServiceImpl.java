package com.cts.moviebooking.service;

import com.cts.moviebooking.dto.UserDetailsResponseDto;
import com.cts.moviebooking.exception.ExternalServiceException;
import com.cts.moviebooking.exception.InvalidCredentialsException;
import com.cts.moviebooking.feign.RegistrationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final RegistrationClient registrationClient;

    @Autowired
    public LoginServiceImpl(RegistrationClient registrationClient) {
        this.registrationClient = registrationClient;
    }

    @Override
    public ResponseEntity<?> getLoginDetails(String username, String password) {
        try {
            ResponseEntity<?> responseEntity = registrationClient.getUserByUserNameAndPassword(username, password);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            } else if (responseEntity.getStatusCode().is4xxClientError()) {
                System.out.println("hi");
                throw new InvalidCredentialsException("Invalid username or password");
            } else {
                throw new ExternalServiceException("Error calling user service");
            }
        } catch (InvalidCredentialsException e) {
            // Re-throw the same exception if it was already an InvalidCredentialsException
            throw e;
        } catch (Exception e) {
            throw new ExternalServiceException("Error calling user service");
        }
    }

}
