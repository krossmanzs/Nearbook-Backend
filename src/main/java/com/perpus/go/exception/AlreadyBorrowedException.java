package com.perpus.go.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AlreadyBorrowedException extends RuntimeException{
    public AlreadyBorrowedException(String message) {
        super(message);
    }

    public AlreadyBorrowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
