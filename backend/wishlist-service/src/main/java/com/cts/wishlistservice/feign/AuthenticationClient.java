package com.cts.wishlistservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "authentication-service")
public interface AuthenticationClient {
    @PostMapping("/api/v1.0/auth/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token, @RequestParam String username);
}
