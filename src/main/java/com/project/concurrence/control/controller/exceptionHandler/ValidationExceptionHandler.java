package com.project.concurrence.control.controller.exceptionHandler;

import com.project.concurrence.control.exception.InvalidAmountTransactionException;
import com.project.concurrence.control.exception.InvalidEnumTypeException;
import com.project.concurrence.control.exception.InvalidTransactionException;
import com.project.concurrence.control.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ValidationExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
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
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .build();
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidAmountTransactionException.class)
    public ErrorResponse invalidAmountTransactionException(InvalidAmountTransactionException exception, ServerHttpRequest request){
        return invalidAmountTransactionResponseOf(exception, request);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidEnumTypeException.class)
    public ErrorResponse invalidTypeEnumExceptionHandler(InvalidEnumTypeException exception, ServerHttpRequest request){
        return invalidEnumTypeResponseOf(exception, request);
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
        log.error("==== Error: [{}]. Path: [{}]. Method: [{}]. Code: [{}] ====", exception.getMessage(), request.getURI().getPath(), request.getMethod(), exception.getCode());
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
        log.error("==== Error: [{}]. Path: [{}]. Method: [{}]. Code: [{}] ====", exception.getMessage(), request.getURI().getPath(), request.getMethod(), exception.getCode());
        return ErrorResponse
                .builder()
                .message(exception.getMessage())
                .status(exception.getCode())
                .timestamp(System.currentTimeMillis())
                .path(request.getURI().getPath())
                .error(exception.getStatus().name().toLowerCase())
                .build();
    }

    private ErrorResponse invalidEnumTypeResponseOf(InvalidEnumTypeException exception, ServerHttpRequest request) {
        log.info("==== Error: [{}]. Path: [{}]. Method: [{}]. Code: [{}] ====", exception.getMessage(), request.getURI().getPath(), request.getMethod(), exception.getCode());
        return ErrorResponse
                .builder()
                .message(exception.getMessage())
                .status(exception.getCode())
                .timestamp(System.currentTimeMillis())
                .path(request.getURI().getPath())
                .error(exception.getStatus().name().toLowerCase())
                .build();
    }

    private ErrorResponse invalidAmountTransactionResponseOf(InvalidAmountTransactionException exception, ServerHttpRequest request){
        log.error("==== Error: [{}]. Path: [{}]. Method: [{}]. Code: [{}] ====", exception.getMessage(), request.getURI().getPath(), request.getMethod(), exception.getCode());
        return ErrorResponse
                .builder()
                .message(exception.getMessage())
                .status(exception.getCode())
                .timestamp(System.currentTimeMillis())
                .path(request.getURI().getPath())
                .error(exception.getStatus().name().toLowerCase())
                .build();
    }
}
