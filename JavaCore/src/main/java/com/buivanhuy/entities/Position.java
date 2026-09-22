package com.buivanhuy.entities;

import com.buivanhuy.app.enums.PositionName;
import com.buivanhuy.utils.database.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Position extends BaseEntity {
    private int id;
    PositionName name;
}
