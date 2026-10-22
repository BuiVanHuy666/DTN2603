package com.buivanhuy.accountcrud.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A custom validation annotation to check if a value is in a list of valid values.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InEnumValidator.class)
public @interface InEnum {
    Class<? extends Enum<?>> enumClass();

    String message() default "The selected value is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
