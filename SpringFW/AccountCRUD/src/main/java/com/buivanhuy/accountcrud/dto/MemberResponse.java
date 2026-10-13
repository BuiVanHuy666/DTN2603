package com.buivanhuy.accountcrud.dto;

import com.buivanhuy.accountcrud.entities.GroupAccount;

import java.time.LocalDateTime;

public record MemberResponse(
        Long accountId,
        String username,
        String fullname,
        String email,
        LocalDateTime joinDate
) {
    public static MemberResponse from(GroupAccount ga) {
        return new MemberResponse(
                ga.getAccount().getId(),
                ga.getAccount().getUsername(),
                ga.getAccount().getFullname(),
                ga.getAccount().getEmail(),
                ga.getJoinDate()
        );
    }
}
