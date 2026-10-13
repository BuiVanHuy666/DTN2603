package com.buivanhuy.accountcrud.dto;

import com.buivanhuy.accountcrud.entities.Group;

import java.time.LocalDateTime;

public record GroupResponse(
        Long id,
        String name,
        LocalDateTime createdDate,
        Long creatorId,
        String creatorName
) {
    public static GroupResponse from(Group g) {
        return new GroupResponse(
                g.getId(),
                g.getName(),
                g.getCreatedDate(),
                g.getCreator() != null ? g.getCreator().getId() : null,
                g.getCreator() != null ? g.getCreator().getFullname() : null
        );
    }
}
