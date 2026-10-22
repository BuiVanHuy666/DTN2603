package com.buivanhuy.accountcrud.dto.request;

import com.buivanhuy.accountcrud.entities.Account;
import com.buivanhuy.accountcrud.entities.Department;
import com.buivanhuy.accountcrud.entities.Position;
import com.buivanhuy.accountcrud.enums.Gender;
import com.buivanhuy.accountcrud.validations.Exists;
import com.buivanhuy.accountcrud.validations.InEnum;
import com.buivanhuy.accountcrud.validations.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AccountPOSTRequest(
        @NotBlank(message = "Username is required")
        @Unique(entityClass = Account.class, fieldName = "username")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Email is not valid")
        @Unique(entityClass = Account.class, fieldName = "email")
        String email,

        @NotBlank(message = "Full name is required")
        String fullname,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,

        @NotBlank(message = "Gender is required")
        @InEnum(enumClass = Gender.class, message = "Invalid gender")
        String gender,

        @NotNull(message = "Department is required")
        @Exists(entityClass = Department.class, message = "Department not found", fieldName = "id")
        Long departmentId,

        @NotNull(message = "Position is required")
        @Exists(entityClass = Position.class, message = "Position not found", fieldName = "id")
        Long positionId
) {
}
