package com.cts.movieservice.exception;

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


    public ErrorMessage(Date timeStamp, String message, HttpStatus status) {
        super();
        this.timeStamp = timeStamp;
        this.message = message;
        this.status = status;
    }

    public ErrorMessage() {
        super();
    }



}
