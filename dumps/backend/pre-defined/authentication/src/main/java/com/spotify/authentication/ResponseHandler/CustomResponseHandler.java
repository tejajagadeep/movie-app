package com.spotify.authentication.ResponseHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CustomResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("message",message);
        map.put("status", status);
        map.put("responseObject",responseObj);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
