package com.cloud.apigateway.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;

import javax.naming.ServiceUnavailableException;
import java.security.SignatureException;
import java.util.Date;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        ErrorResponse messageResponse = new ErrorResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimeStamp(new Date());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        ex.getBindingResult().getAllErrors().forEach(error -> errorResponse.setMessage(error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {

        ErrorResponse response = new ErrorResponse();
        String message = ex.getMessage();
        String template = "messageTemplate='";
        String messageTemplate = message.substring(message.indexOf(template) + template.length(), message.indexOf("'", message.indexOf(template) + template.length()));
        response.setMessage(messageTemplate);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setTimeStamp(new Date());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
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

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<Object> handleSERVICE_UNAVAILABLE(ServiceUnavailableException ex) {
        ErrorResponse messageResponse = new ErrorResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.SERVICE_UNAVAILABLE);
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

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleMalformedJwtException(MalformedJwtException ex) {
        ErrorResponse messageResponse = new ErrorResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.UNAUTHORIZED);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.UNAUTHORIZED);
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
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleSignatureException(SignatureException ex) {
        ErrorResponse messageResponse = new ErrorResponse();
        messageResponse.setMessage(ex.getMessage());
        messageResponse.setStatus(HttpStatus.BAD_REQUEST);
        messageResponse.setTimeStamp(new Date());
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }
}
