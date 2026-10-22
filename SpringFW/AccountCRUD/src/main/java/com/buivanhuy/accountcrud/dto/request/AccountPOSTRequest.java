package com.buivanhuy.accountcrud.dto.request;

import com.buivanhuy.accountcrud.entities.Account;
import com.buivanhuy.accountcrud.entities.Department;
import com.buivanhuy.accountcrud.entities.Position;
import com.buivanhuy.accountcrud.enums.Gender;
import com.buivanhuy.accountcrud.validations.Exists;
import com.buivanhuy.accountcrud.validations.InEnum;
import com.buivanhuy.accountcrud.validations.Unique;
import jakarta.validation.constraints.*;

public record AccountPOSTRequest(
        @NotBlank(message = "Username is required")
        @Size(min = 4, max = 255, message = "Username must be between 4 and 255 characters")
        @Unique(entityClass = Account.class, fieldName = "username", message = "Username already exists")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Email is not valid")
        @Size(max = 255, message = "Email must not exceed 255 characters")
        @Unique(entityClass = Account.class, fieldName = "email", message = "Email already exists")
        String email,

        @NotBlank(message = "Full name is required")
        @Size(max = 255, message = "Full name must not exceed 255 characters")
        String fullname,

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
        String password,

        @Size(max = 50, message = "Gender must not exceed 50 characters")
        @InEnum(enumClass = Gender.class, message = "Gender is not valid")
        String gender,

        @Positive(message = "Department ID must be positive")
        @NotNull(message = "Department ID is required")
        @Exists(entityClass = Department.class, fieldName = "id", message = "Department not found")
        Long departmentId,

        @Positive(message = "Position ID must be positive")
        @NotNull(message = "Position ID is required")
        @Exists(entityClass = Position.class, fieldName = "id", message = "Position not found")
        Long positionId
) {
}