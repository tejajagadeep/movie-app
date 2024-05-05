package com.cts.wishlistservice.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.SignatureException;
import java.util.Date;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        ErrorResponse messageResponse = new ErrorResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
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
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
        ErrorResponse messageResponse = new ErrorResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<Object> handleUnAuthorizedException(UnAuthorizedException ex) {
        ErrorResponse messageResponse = new ErrorResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.UNAUTHORIZED);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse messageResponse = new ErrorResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.BAD_REQUEST);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleSignatureException(SignatureException ex) {
        ErrorResponse messageResponse = new ErrorResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.BAD_REQUEST);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {
        ErrorResponse messageResponse = new ErrorResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.BAD_REQUEST);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleMalformedJwtException(MalformedJwtException ex) {
        ErrorResponse messageResponse = new ErrorResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.BAD_REQUEST);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

}
