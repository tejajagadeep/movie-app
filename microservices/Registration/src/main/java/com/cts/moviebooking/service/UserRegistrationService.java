package com.cts.moviebooking.service;

import com.cts.moviebooking.dto.UserDetailsRequestDto;
import com.cts.moviebooking.dto.UserDetailsResponseDto;
import org.springframework.stereotype.Service;

public interface UserRegistrationService {

    public UserDetailsResponseDto createUser(UserDetailsRequestDto userDetails);


    UserDetailsResponseDto getByUserNameAndPassword(String userName, String password);


}
