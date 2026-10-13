package com.buivanhuy.accountcrud.dto;

import jakarta.validation.constraints.NotBlank;

public record GroupRequest(
        @NotBlank String name,
        Long creatorId
) {
}
