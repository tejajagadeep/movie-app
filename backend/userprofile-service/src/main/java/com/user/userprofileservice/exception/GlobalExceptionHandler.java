package com.user.userprofileservice.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.kafka.common.KafkaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomResponse> handleInvalidUserException(ResourceNotFoundException ex) {
        CustomResponse messageResponse = new CustomResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.NOT_FOUND);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);

    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        CustomResponse messageResponse = new CustomResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.CONFLICT);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
        CustomResponse messageResponse = new CustomResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<Object> handleUnAuthorizedException(UnAuthorizedException ex) {
        CustomResponse messageResponse = new CustomResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.UNAUTHORIZED);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServerConnectionException.class)
    public ResponseEntity<Object> handleServerConnectionException(ServerConnectionException ex) {
        CustomResponse messageResponse = new CustomResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleMalformedJwtException(MalformedJwtException ex) {
        CustomResponse messageResponse = new CustomResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.UNAUTHORIZED);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.UNAUTHORIZED);
    }
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {
        CustomResponse messageResponse = new CustomResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.UNAUTHORIZED);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(KafkaException.class)
    public ResponseEntity<Object> handleKafkaException(KafkaException ex) {
        CustomResponse messageResponse = new CustomResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
