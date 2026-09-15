package com.buivanhuy.services;

import com.buivanhuy.entities.Position;
import com.buivanhuy.enums.PositionName;
import com.buivanhuy.repositories.PositionRepositoryImpl;
import com.buivanhuy.repositories.ResourceRepository;

public class PositionService {
    private final ResourceRepository<Position> posRepo = new PositionRepositoryImpl();

    public String getPositionNameById(int id) {
        return String.valueOf(PositionName.valueOf(String.valueOf(this.posRepo.findById(id).getName())));
    }
}
