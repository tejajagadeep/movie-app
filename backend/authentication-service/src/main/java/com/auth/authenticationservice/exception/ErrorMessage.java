package com.auth.authenticationservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Setter
@Getter
public class ErrorMessage {
    private Date timeStamp;
    private String message;
    private HttpStatus status;


    public ErrorMessage() {
        super();
    }



}