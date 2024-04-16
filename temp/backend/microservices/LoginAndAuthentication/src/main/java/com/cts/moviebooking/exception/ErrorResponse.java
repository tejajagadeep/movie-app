package com.cts.moviebooking.exception;

import java.util.Date;

public class ErrorResponse {
    private Date timestamp;
    private int status;
    private String message;

    public ErrorResponse(Date timestamp, int status, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}