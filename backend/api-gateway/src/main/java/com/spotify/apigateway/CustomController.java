package com.spotify.apigateway;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomController {

    @GetMapping
    public String hello(){
        return "Api Gateway Microservice for Spotify App started successfully.";
    }
}
