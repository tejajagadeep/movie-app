package com.cts.wishlistservice.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@ToString
public class ErrorResponse {
    private Date timeStamp;
    private String message;
    private HttpStatus status;

    public ErrorResponse() {
        super();
    }
}