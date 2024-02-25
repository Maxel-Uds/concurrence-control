package com.project.concurrence.control.controller.customValidators;

import com.project.concurrence.control.controller.customValidators.impl.LongValueValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Documented
@Constraint(validatedBy = LongValueValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull()
@ReportAsSingleViolation
public @interface LongValueValidator {

    String message() default "Valor não válido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
