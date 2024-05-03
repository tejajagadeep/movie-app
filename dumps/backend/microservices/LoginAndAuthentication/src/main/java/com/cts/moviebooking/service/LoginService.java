package com.cts.moviebooking.service;

import com.cts.moviebooking.dto.UserDetailsResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    public ResponseEntity<?> getLoginDetails(String username, String password) ;
}
