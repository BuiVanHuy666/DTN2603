package com.buivanhuy.accountcrud.repositories;

import com.buivanhuy.accountcrud.entities.Position;
import com.buivanhuy.accountcrud.enums.PositionName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
    boolean existsByName(PositionName name);
}
