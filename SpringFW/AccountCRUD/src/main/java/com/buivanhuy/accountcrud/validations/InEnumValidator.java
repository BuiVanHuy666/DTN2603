package com.buivanhuy.accountcrud.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class InEnumValidator implements ConstraintValidator<InEnum, String> {
    private List<String> allowedValues;

    @Override
    public void initialize(InEnum constraintAnnotation) {
        this.allowedValues = Arrays.stream(constraintAnnotation.enumClass().getEnumConstants())
                .map(e -> e.name().toLowerCase())
                .toList();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return allowedValues.contains(value.toLowerCase());
    }
}