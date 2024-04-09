package com.cts.moviebooking.exception;

public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException(String message) {

        super(message);
    }
}
