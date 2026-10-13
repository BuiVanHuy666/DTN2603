package com.buivanhuy.accountcrud.dto;

import com.buivanhuy.accountcrud.entities.Account;
import com.buivanhuy.accountcrud.enums.Gender;

import java.time.LocalDateTime;

public record AccountResponse(
        Long id,
        String username,
        String email,
        String fullname,
        Gender gender,
        LocalDateTime createDate,
        String departmentName,
        String positionName
) {
    public static AccountResponse from(Account a) {
        return new AccountResponse(
                a.getId(),
                a.getUsername(),
                a.getEmail(),
                a.getFullname(),
                a.getGender(),
                a.getCreateDate(),
                a.getDepartment() != null ? a.getDepartment().getName() : null,
                a.getPosition() != null ? a.getPosition().getName().name() : null
        );
    }
}
