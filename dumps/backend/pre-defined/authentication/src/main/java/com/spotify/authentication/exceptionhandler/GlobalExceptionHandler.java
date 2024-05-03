package com.spotify.authentication.exceptionhandler;

import com.spotify.authentication.ResponseHandler.CustomResponseHandler;
import com.spotify.authentication.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException){
        return CustomResponseHandler.generateResponse("Message: "+userAlreadyExistsException.getMessage(), HttpStatus.CONFLICT, userAlreadyExistsException);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException){
        return CustomResponseHandler.generateResponse("Message: "+usernameNotFoundException.getMessage(), HttpStatus.NOT_FOUND, usernameNotFoundException);
    }
}
