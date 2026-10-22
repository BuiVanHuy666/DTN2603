package com.buivanhuy.accountcrud.dto.request;

import com.buivanhuy.accountcrud.entities.Department;
import com.buivanhuy.accountcrud.entities.Position;
import com.buivanhuy.accountcrud.enums.Gender;
import com.buivanhuy.accountcrud.validations.Exists;
import com.buivanhuy.accountcrud.validations.InEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AccountPATCHRequest(
        @Size(min = 4, max = 255, message = "Username must be between 4 and 255 characters")
        String username,

        @Email(message = "Email is not valid")
        @Size(max = 255, message = "Email must not exceed 255 characters")
        String email,

        @Size(max = 255, message = "Full name must not exceed 255 characters")
        String fullname,

        @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
        String password,

        @Size(max = 50, message = "Gender must not exceed 50 characters")
        @InEnum(enumClass = Gender.class, message = "Gender is not valid")
        String gender,

        @Positive(message = "Department ID must be positive")
        @NotNull(message = "Department ID is required")
        @Exists(entityClass = Department.class, fieldName = "id", message = "Department does not exist")
        Long departmentId,

        @Positive(message = "Position ID must be positive")
        @NotNull(message = "Position ID is required")
        @Exists(entityClass = Position.class, fieldName = "id", message = "Position does not exist")
        Long positionId
) {
}