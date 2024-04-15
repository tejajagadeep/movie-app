package com.user.userprofileservice.controller;

import com.user.userprofileservice.dto.UserProfileDto;
import com.user.userprofileservice.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CustomController {

    @GetMapping
    @Operation(summary = "Swagger Endpoint", description = "This endpoint will direct to Swagger end point.", hidden = true)
    public void custom(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
}
