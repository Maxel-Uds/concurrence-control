package com.project.concurrence.control.controller.exceptionHandler;

import com.project.concurrence.control.exception.InvalidAmountTransactionException;
import com.project.concurrence.control.exception.InvalidEnumTypeException;
import com.project.concurrence.control.exception.InvalidTransactionException;
import com.project.concurrence.control.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ValidationExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validationExceptionHandler(MethodArgumentNotValidException exception){

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
        return ResponseEntity.unprocessableEntity().body(
                ValidationErrorResponse
                .builder()
                .errors(errors)
                .timestamp(System.currentTimeMillis())
                .message("Input validation error")
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .build()
        );
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidAmountTransactionException.class)
    public ResponseEntity<ErrorResponse> invalidAmountTransactionException(InvalidAmountTransactionException exception, HttpServletRequest request){
        return ResponseEntity.unprocessableEntity().body(invalidAmountTransactionResponseOf(exception, request));
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidEnumTypeException.class)
    public ResponseEntity<ErrorResponse> invalidTypeEnumExceptionHandler(InvalidEnumTypeException exception, HttpServletRequest request){
        return ResponseEntity.unprocessableEntity().body(invalidEnumTypeResponseOf(exception, request));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundException(UserNotFoundException exception, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resourceNotFoundRequestResponseOf(exception, request));
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<ErrorResponse> invalidTransactionException(InvalidTransactionException exception, HttpServletRequest request){
        return ResponseEntity.unprocessableEntity().body(invalidTransactionResponseOf(exception, request));
    }

    private ErrorResponse invalidTransactionResponseOf(InvalidTransactionException exception, HttpServletRequest request){
        this.logRequest(exception.getMessage(), request.getRequestURI(), request.getMethod(), exception.getCode());
        return ErrorResponse
                .builder()
                .message(exception.getMessage())
                .status(exception.getCode())
                .timestamp(System.currentTimeMillis())
                .path(request.getRequestURI())
                .error(exception.getStatus().name().toLowerCase())
                .build();
    }

    private ErrorResponse resourceNotFoundRequestResponseOf(UserNotFoundException exception, HttpServletRequest request){
        this.logRequest(exception.getMessage(), request.getRequestURI(), request.getMethod(), exception.getCode());
        return ErrorResponse
                .builder()
                .message(exception.getMessage())
                .status(exception.getCode())
                .timestamp(System.currentTimeMillis())
                .path(request.getRequestURI())
                .error(exception.getStatus().name().toLowerCase())
                .build();
    }

    private ErrorResponse invalidEnumTypeResponseOf(InvalidEnumTypeException exception, HttpServletRequest request) {
        this.logRequest(exception.getMessage(), request.getRequestURI(), request.getMethod(), exception.getCode());
        return ErrorResponse
                .builder()
                .message(exception.getMessage())
                .status(exception.getCode())
                .timestamp(System.currentTimeMillis())
                .path(request.getRequestURI())
                .error(exception.getStatus().name().toLowerCase())
                .build();
    }

    private ErrorResponse invalidAmountTransactionResponseOf(InvalidAmountTransactionException exception, HttpServletRequest request){
        this.logRequest(exception.getMessage(), request.getRequestURI(), request.getMethod(), exception.getCode());
        return ErrorResponse
                .builder()
                .message(exception.getMessage())
                .status(exception.getCode())
                .timestamp(System.currentTimeMillis())
                .path(request.getRequestURI())
                .error(exception.getStatus().name().toLowerCase())
                .build();
    }

    private void logRequest(String error, String path, String method, int code) {
        log.error("==== Error: [{}]. Path: [{}]. Method: [{}]. Code: [{}] ====", error, path, method, code);

    }
}
