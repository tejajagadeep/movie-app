package com.spotify.userprofile.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(value = "authentication-service")
public interface AuthenticationClient {
    @PostMapping("/api/v1.0/auth/validate")
    public ResponseEntity<Map<String,String>> validateToken(@RequestHeader("Authorization") String token);
}
