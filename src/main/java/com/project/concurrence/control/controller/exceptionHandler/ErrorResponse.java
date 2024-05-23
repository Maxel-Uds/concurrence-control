package com.project.concurrence.control.controller.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class ErrorResponse {

    private final long timestamp;
    private final int status;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String error;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String path;
}
