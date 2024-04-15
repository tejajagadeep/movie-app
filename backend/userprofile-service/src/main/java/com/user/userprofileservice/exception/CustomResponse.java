package com.user.userprofileservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Setter
@Getter
public class CustomResponse {
    private Date timeStamp;
    private String message;
    private HttpStatus status;

    public CustomResponse() {
        super();
    }



}