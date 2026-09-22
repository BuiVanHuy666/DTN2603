package com.buivanhuy.app.repositories;

import com.buivanhuy.utils.database.BaseEntity;

import java.util.List;

public interface ResourceRepository<T extends BaseEntity> {
    List<T> findAll();
    T findById(int id);
    boolean store(T entity);
    boolean update(T entity);
    boolean delete(T entity);
}