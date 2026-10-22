package com.buivanhuy.accountcrud.dto;

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

public record AccountDTO(
        @NotBlank
        @Unique(entityClass = Account.class, fieldName = "username")
        String username,

        @NotBlank
        @Email
        @Unique(entityClass = Account.class, fieldName = "email")

        String email,

        @NotBlank
        String fullname,

        @NotBlank @Size(min = 6)
        String password,

        @NotBlank
        @InEnum(enumClass = Gender.class, message = "Invalid gender")
        String gender,

        @NotNull
        @Exists(entityClass = Department.class, message = "Department not found", fieldName = "id")
        Long departmentId,

        @NotNull
        @Exists(entityClass = Position.class, message = "Position not found", fieldName = "id")
        Long positionId
) {
}
