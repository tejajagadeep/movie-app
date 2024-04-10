package com.cts.wishlistservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
public class ErrorResponse {
    private Date timeStamp;
    private String message;
    private HttpStatus status;


    public ErrorResponse(Date timeStamp, String message, HttpStatus status) {
        super();
        this.timeStamp = timeStamp;
        this.message = message;
        this.status = status;
    }

    public ErrorResponse() {
        super();
    }



}