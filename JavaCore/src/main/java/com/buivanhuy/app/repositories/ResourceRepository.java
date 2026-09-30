package com.buivanhuy.app.repositories;

import com.buivanhuy.utils.database.BaseEntity;
import java.util.List;

/**
 * Generic repository interface providing basic CRUD (Create, Read, Update, Delete) operations.
 * This structure implements the Repository/DAO Design Pattern, effectively decoupling
 * business logic from data access mechanisms.
 *
 * @param <T> The type of the entity. Must extend {@link BaseEntity}.
 */
public interface ResourceRepository<T extends BaseEntity> {

    /**
     * Retrieves all records from the database.
     *
     * @return A {@link List} containing all entities of type {@code T}. Returns an empty list if no records are found.
     */
    List<T> findAll();

    /**
     * Finds an entity based on its unique identifier.
     *
     * @param id The primary key of the entity to retrieve.
     * @return The entity object if found, or {@code null} if no record matches the given id.
     */
    T findById(int id);

    /**
     * Persists a new entity into the database.
     *
     * @param entity The entity object to be saved. Must not be {@code null}.
     * @return {@code true} if the entity was successfully saved, {@code false} otherwise.
     */
    boolean store(T entity);

    /**
     * Updates an existing entity in the database.
     *
     * @param entity The entity object containing the updated data. The entity's ID must already exist in the database.
     * @return {@code true} if the update was successful, {@code false} otherwise.
     */
    boolean update(T entity);

    /**
     * Removes an entity from the database.
     *
     * @param entity The entity object to be deleted.
     * @return {@code true} if the deletion was successful, {@code false} otherwise.
     */
    boolean delete(T entity);
}