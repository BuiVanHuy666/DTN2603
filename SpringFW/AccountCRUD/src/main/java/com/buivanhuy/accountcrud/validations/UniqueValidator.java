package com.buivanhuy.accountcrud.validations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entityClass;
    private String fieldName;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.entityClass = constraintAnnotation.entityClass();
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null || (value instanceof String str && str.isBlank())) {
            return true;
        }

        String jpql = String.format("SELECT count(e) FROM %s e WHERE e.%s = :value",
                entityClass.getSimpleName(), fieldName);

        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("value", value)
                .getSingleResult();

        return count == 0;
    }
}