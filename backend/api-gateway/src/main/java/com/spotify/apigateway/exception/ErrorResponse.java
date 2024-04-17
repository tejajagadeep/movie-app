package com.spotify.apigateway.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Setter
@Getter
public class ErrorResponse {
    private Date timeStamp;
    private String message;
    private HttpStatus status;

    public ErrorResponse() {
        super();
    }



}