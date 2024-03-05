package com.project.concurrence.control.controller.customValidators;

import com.project.concurrence.control.controller.customValidators.impl.TransactionTypeEnumValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = TransactionTypeEnumValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull()
@ReportAsSingleViolation
public @interface EnumValidator {

    Class<? extends Enum<?>> enumClazz();

    String message() default "Valor não válido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}