package com.spotify.userProfile.ExceptionHandler;

import com.spotify.userProfile.Exceptions.UserAlreadyExistsException;
import com.spotify.userProfile.ResponseHandler.CustomResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException){
        return CustomResponseHandler.generateResponse("Error: User already exist!", HttpStatus.CONFLICT, userAlreadyExistsException);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleException(Exception ex) {
        return CustomResponseHandler.generateResponse("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
}
