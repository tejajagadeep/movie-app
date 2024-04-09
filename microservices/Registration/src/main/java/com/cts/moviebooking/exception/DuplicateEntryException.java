package com.cts.moviebooking.exception;

public class DuplicateEntryException extends RuntimeException{

    public DuplicateEntryException(String message) {

        super(message);
    }
}
