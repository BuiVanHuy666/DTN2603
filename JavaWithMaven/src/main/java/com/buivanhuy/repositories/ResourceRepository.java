package com.buivanhuy.repositories;

import com.buivanhuy.utils.database.BaseEntity;

import java.util.List;

public interface ResourceRepository<T extends BaseEntity> {
    List<T> findAll();
    T findById(int id);
    T store(T entity);
    T update(T entity);
    boolean delete(T entity);
}