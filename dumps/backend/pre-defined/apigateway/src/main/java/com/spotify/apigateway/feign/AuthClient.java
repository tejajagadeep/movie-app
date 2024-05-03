package com.spotify.apigateway.feign;

import com.spotify.apigateway.models.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "http://localhost:8081", name = "AUTHENTICATION-SERVICE")
public interface AuthClient {
    @GetMapping("/validateToken")
    public AuthResponse validate(@RequestHeader("Authorization") final String token);
}
