package com.user.userprofileservice.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidateExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomResponse> handleValidationException(ValidationException ex) {

        CustomResponse response = new CustomResponse();
        String message = ex.getMessage();
        String messageTemplate = message.substring(message.indexOf("messageTemplate='") + "messageTemplate='".length(), message.indexOf("'", message.indexOf("messageTemplate='") + "messageTemplate='".length()));
        response.setMessage(messageTemplate);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setTimeStamp(new Date());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
