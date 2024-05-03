package com.spotify.userprofile.exception;

import java.io.Serial;

public class UnAuthorizedException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public UnAuthorizedException(String message) {
        super(message);
    }
}
