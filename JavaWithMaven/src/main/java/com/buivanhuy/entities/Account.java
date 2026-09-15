package com.buivanhuy.entities;

import com.buivanhuy.enums.Gender;
import com.buivanhuy.utils.database.BaseEntity;
import com.buivanhuy.utils.database.DB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {
    private String fullName;
    private String username;
    private String email;
    private Date createDate;
    private Department department;
    private Position position;
    private Gender gender;
}
