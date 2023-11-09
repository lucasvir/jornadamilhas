package com.jornadamilhas.api.services.exceptions;

import org.springframework.http.HttpStatus;

public class NotValidException extends RuntimeException {

    private final HttpStatus statusCode;

    public NotValidException(String msg, HttpStatus status) {
        super(msg);
        this.statusCode = status;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
