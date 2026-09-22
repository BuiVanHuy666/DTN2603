package com.buivanhuy.utils.database;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public abstract class BaseEntity {
    @Getter
    @Setter
    private Date createdAt;

    @Getter
    @Setter
    private Date updatedAt;
}