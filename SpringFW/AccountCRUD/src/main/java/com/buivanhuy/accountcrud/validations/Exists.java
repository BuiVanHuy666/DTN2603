package com.buivanhuy.accountcrud.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsValidator.class)
public @interface Exists {
    Class<?> entityClass();

    String message() default "Entity not found";

    String fieldName();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
