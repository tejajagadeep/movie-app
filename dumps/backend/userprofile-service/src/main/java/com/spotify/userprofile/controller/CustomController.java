package com.spotify.userprofile.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
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
