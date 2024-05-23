package com.project.concurrence.control.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final int code;
    private final HttpStatus status;

    public UserNotFoundException(String message, int code, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
