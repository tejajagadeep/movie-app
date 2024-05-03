package com.spotify.apigateway.exceptionhandler;

import com.spotify.apigateway.exceptions.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException invalidTokenException){
        return new ResponseEntity<>("Unauthorized Access!", HttpStatus.UNAUTHORIZED);
    }
}
