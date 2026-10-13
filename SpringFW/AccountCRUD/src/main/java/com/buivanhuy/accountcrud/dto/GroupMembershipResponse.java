package com.buivanhuy.accountcrud.dto;

import com.buivanhuy.accountcrud.entities.GroupAccount;

import java.time.LocalDateTime;

public record GroupMembershipResponse(
        Long groupId,
        String groupName,
        LocalDateTime joinDate
) {
    public static GroupMembershipResponse from(GroupAccount ga) {
        return new GroupMembershipResponse(
                ga.getGroup().getId(),
                ga.getGroup().getName(),
                ga.getJoinDate()
        );
    }
}
