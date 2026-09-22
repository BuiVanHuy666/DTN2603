package com.buivanhuy.app.services;

import com.buivanhuy.app.repositories.ResourceRepository;
import com.buivanhuy.entities.Position;

import java.util.List;

public class PositionServiceImpl implements Manageable<Position> {
    private final ResourceRepository<Position> posRepo;

    public PositionServiceImpl(ResourceRepository<Position> posRepo) {
        this.posRepo = posRepo;
    }

    @Override
    public List<Position> index() {
        return posRepo.findAll();
    }

    @Override
    public Position show(int id) {
        return posRepo.findById(id);
    }

    @Override
    public boolean store(Position entity) {
        return posRepo.store(entity);
    }

    @Override
    public boolean update(Position entity) {
        return posRepo.update(entity);
    }

    @Override
    public boolean destroy(int id) {
        Position pos = posRepo.findById(id);
        if (pos == null) {
            return false;
        }
        return posRepo.delete(pos);
    }
}