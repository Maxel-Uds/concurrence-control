package com.project.concurrence.control.controller.customValidators.impl;

import com.project.concurrence.control.controller.customValidators.LongValueValidator;
import com.project.concurrence.control.exception.InvalidAmountTransactionException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.HttpStatus;

public class LongValueValidatorImpl implements ConstraintValidator<LongValueValidator, String> {

    @Override
    public void initialize(LongValueValidator constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Long.parseLong(value);
            return true;
        } catch (Exception e) {
            throw new InvalidAmountTransactionException(
                "Apenas valores inteiros são permitidos",
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                HttpStatus.UNPROCESSABLE_ENTITY
            );
        }
    }
}
