package com.cloud.swaggerserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "This is the centralized swagger page for all movie microservices.";
    }
}
