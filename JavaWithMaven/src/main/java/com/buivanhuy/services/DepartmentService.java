package com.buivanhuy.services;


import com.buivanhuy.entities.Department;
import com.buivanhuy.repositories.DepartmentRepositoryImpl;
import com.buivanhuy.repositories.ResourceRepository;

public class DepartmentService {
    private final ResourceRepository<Department> deptRepo = new DepartmentRepositoryImpl();

    public String getDepartmentNameById(int id) {
        return this.deptRepo.findById(id).getName();
    }
}
