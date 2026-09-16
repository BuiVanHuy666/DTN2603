package com.buivanhuy.entities;

import com.buivanhuy.app.enums.Gender;
import com.buivanhuy.utils.database.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {
    private int id;
    private String fullName;
    private String username;
    private String email;
    private Date createDate;
    private Department department;
    private Position position;
    private Gender gender;
}
