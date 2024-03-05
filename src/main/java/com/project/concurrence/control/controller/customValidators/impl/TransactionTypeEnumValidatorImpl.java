package com.project.concurrence.control.controller.customValidators.impl;

import com.project.concurrence.control.controller.customValidators.EnumValidator;
import com.project.concurrence.control.exception.InvalidEnumTypeException;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionTypeEnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

    List<String> valueList = null;

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        valueList = Arrays.stream(constraintAnnotation.enumClazz().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!valueList.contains(value)) {
            throw new InvalidEnumTypeException(
                    String.format("Valor [%s] não permitido. Apenas os valores %s são permitidos.", value, valueList),
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }

        return true;
    }

}