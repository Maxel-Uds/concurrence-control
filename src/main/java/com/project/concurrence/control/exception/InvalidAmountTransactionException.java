package com.project.concurrence.control.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidAmountTransactionException extends RuntimeException {
    private final Integer code;
    private final HttpStatus status;

    public InvalidAmountTransactionException(String message, Integer code, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
