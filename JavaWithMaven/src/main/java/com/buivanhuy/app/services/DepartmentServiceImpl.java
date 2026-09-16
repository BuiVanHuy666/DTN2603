package com.buivanhuy.app.services;

import com.buivanhuy.app.repositories.ResourceRepository;
import com.buivanhuy.entities.Department;

import java.util.List;

public class DepartmentServiceImpl implements Manageable<Department> {
    private final ResourceRepository<Department> deptRepo;

    public DepartmentServiceImpl(ResourceRepository<Department> deptRepo) {
        this.deptRepo = deptRepo;
    }

    @Override
    public List<Department> index() {
        return deptRepo.findAll();
    }

    @Override
    public Department show(int id) {
        return deptRepo.findById(id);
    }

    @Override
    public boolean store(Department entity) {
        return deptRepo.store(entity);
    }

    @Override
    public boolean update(Department entity) { return deptRepo.update(entity);}

    @Override
    public boolean destroy(int id) {
        Department dept = deptRepo.findById(id);
        if (dept == null) {
            return false;
        }
        return deptRepo.delete(dept);
    }
}