package com.buivanhuy.accountcrud.dto.request;

import com.buivanhuy.accountcrud.entities.Department;
import com.buivanhuy.accountcrud.entities.Position;
import com.buivanhuy.accountcrud.enums.Gender;
import com.buivanhuy.accountcrud.validations.Exists;
import com.buivanhuy.accountcrud.validations.InEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record AccountPATCHRequest(
        @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
        String username,

        @Email(message = "Email is not valid")
        String email,

        String fullname,

        @InEnum(enumClass = Gender.class, message = "Gender is not valid")
        String gender,

        @Exists(entityClass = Department.class, message = "Department does not exist", fieldName = "id")
        Long departmentId,

        @Exists(entityClass = Position.class, message = "Position does not exist", fieldName = "id")
        Long positionId
) {
}