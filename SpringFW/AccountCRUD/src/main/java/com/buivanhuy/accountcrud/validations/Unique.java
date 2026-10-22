package com.buivanhuy.accountcrud.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
public @interface Unique {
    Class<?> entityClass();

    String fieldName();

    String message() default "The value is already taken.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
