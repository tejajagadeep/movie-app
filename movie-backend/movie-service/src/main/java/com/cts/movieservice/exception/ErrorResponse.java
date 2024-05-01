package com.cts.movieservice.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
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
