package com.cts.moviebooking.service;

import com.cts.moviebooking.dto.BasicUserDetails;
import com.cts.moviebooking.dto.UserDetailsRequestDto;
import com.cts.moviebooking.dto.UserDetailsResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AdminRegistrationService {

    UserDetailsResponseDto createAdmin(UserDetailsRequestDto request);

    List<BasicUserDetails> getAllUsers();
}
