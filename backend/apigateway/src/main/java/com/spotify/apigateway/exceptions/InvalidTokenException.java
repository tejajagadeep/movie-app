package com.spotify.apigateway.exceptions;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String msg){
        super(msg);
    }
}
