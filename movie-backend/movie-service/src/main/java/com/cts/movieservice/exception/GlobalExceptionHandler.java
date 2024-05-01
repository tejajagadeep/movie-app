package com.cts.movieservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        ErrorResponse messageResponse = new ErrorResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.NOT_FOUND);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUserException(ResourceNotFoundException ex) {
        ErrorResponse messageResponse = new ErrorResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.NOT_FOUND);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception ex) {
        ErrorResponse messageResponse = new ErrorResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
        ErrorResponse messageResponse = new ErrorResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
