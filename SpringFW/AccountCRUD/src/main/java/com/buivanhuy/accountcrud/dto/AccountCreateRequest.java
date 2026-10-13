package com.buivanhuy.accountcrud.dto;

import com.buivanhuy.accountcrud.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AccountCreateRequest(
                @NotBlank String username,
                @NotBlank @Email String email,
                @NotBlank String fullname,
                @NotBlank @Size(min = 6) String password,
                @NotNull Gender gender,
                Long departmentId,
                Long positionId) {
}
