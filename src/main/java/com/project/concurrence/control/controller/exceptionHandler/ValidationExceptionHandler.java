package com.project.concurrence.control.controller.exceptionHandler;

import com.project.concurrence.control.model.enums.TransactionType;
import com.project.concurrence.control.exception.InvalidTransactionException;
import com.project.concurrence.control.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ValidationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public ErrorResponse validationExceptionHandler(WebExchangeBindException exception){

        final List<FieldMessage> errors =
                exception.getFieldErrors()
                        .stream()
                        .map(fieldError ->
                                FieldMessage.builder()
                                        .fieldName(fieldError.getField())
                                        .fieldError(fieldError.getDefaultMessage())
                                        .build())
                        .collect(Collectors.toList());

        log.error("==== Input error ====");
        return ValidationErrorResponse
                .builder()
                .errors(errors)
                .timestamp(System.currentTimeMillis())
                .message("Input validation error")
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(TypeMismatchException.class)
    public ErrorResponse typeMismatchExceptionHandler(TypeMismatchException exception, ServerHttpRequest request){
        return typeMismatchEnumResponseOf(exception, request);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse resourceNotFoundException(UserNotFoundException exception, ServerHttpRequest request){
        return resourceNotFoundRequestResponseOf(exception, request);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidTransactionException.class)
    public ErrorResponse invalidTransactionException(InvalidTransactionException exception, ServerHttpRequest request){
        return invalidTransactionResponseOf(exception, request);
    }

    private ErrorResponse invalidTransactionResponseOf(InvalidTransactionException exception, ServerHttpRequest request){
        log.error("==== Error: [{}]. Path: [{}]. Method: [{}]. Code: [{}] ====", exception.getMessage(), request.getURI().getPath(), request.getMethod(), HttpStatus.NOT_FOUND.value());
        return ErrorResponse
                .builder()
                .message(exception.getMessage())
                .status(exception.getCode())
                .timestamp(System.currentTimeMillis())
                .path(request.getURI().getPath())
                .error(exception.getStatus().name().toLowerCase())
                .build();
    }

    private ErrorResponse resourceNotFoundRequestResponseOf(UserNotFoundException exception, ServerHttpRequest request){
        log.error("==== Error: [{}]. Path: [{}]. Method: [{}]. Code: [{}] ====", exception.getMessage(), request.getURI().getPath(), request.getMethod(), HttpStatus.NOT_FOUND.value());
        return ErrorResponse
                .builder()
                .message(exception.getMessage())
                .status(exception.getCode())
                .timestamp(System.currentTimeMillis())
                .path(request.getURI().getPath())
                .error(exception.getStatus().name().toLowerCase())
                .build();
    }

    private ErrorResponse typeMismatchEnumResponseOf(TypeMismatchException exception, ServerHttpRequest request){
        return ErrorResponse
                .builder()
                .message("Error, just this operations are accepted: " + Arrays.toString(TransactionType.values()))
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .timestamp(System.currentTimeMillis())
                .path(request.getURI().getPath())
                .error(HttpStatus.UNPROCESSABLE_ENTITY.name().toLowerCase())
                .build();
    }

    private ErrorResponse badRequestResponseOf(RuntimeException exception, ServerHttpRequest request){
        log.error("==== Error: [{}]. Path: [{}]. Method: [{}]. Code: [{}] ====", exception.getMessage(), request.getURI().getPath(), request.getMethod(), HttpStatus.BAD_REQUEST.value());
        return ErrorResponse
                .builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(System.currentTimeMillis())
                .path(request.getURI().getPath())
                .error(HttpStatus.BAD_REQUEST.name().toLowerCase())
                .build();
    }
}
