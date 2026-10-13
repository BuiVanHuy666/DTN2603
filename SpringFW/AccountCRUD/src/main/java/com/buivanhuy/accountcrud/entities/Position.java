package com.buivanhuy.accountcrud.entities;

import com.buivanhuy.accountcrud.enums.PositionName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "position")
public class Position extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private PositionName name;
}
