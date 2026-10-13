package com.buivanhuy.accountcrud.repositories;

import com.buivanhuy.accountcrud.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByName(String name);
}
