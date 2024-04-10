package com.auth.authenticationservice.exception;

import java.io.Serial;

public class CustomUnAuthorizedException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public CustomUnAuthorizedException(String message) {
        super(message);
    }
}
