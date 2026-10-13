package com.buivanhuy.accountcrud.dto;

import com.buivanhuy.accountcrud.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountUpdateRequest(
        @NotBlank String username,
        @NotBlank @Email String email,
        @NotBlank String fullname,
        @NotNull Gender gender,
        Long departmentId,
        Long positionId
) {
}
