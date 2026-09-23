package com.buivanhuy.app.services;

import com.buivanhuy.utils.database.BaseEntity;

import java.util.List;

public interface Manageable<T extends BaseEntity> {
    /**
     * Retrieves all resources.
     *
     * @return a list containing all resources
     */
    List<T> index();

    /**
     * Retrieves a resource by its ID.
     *
     * @param id the ID of the resource to retrieve
     * @return the resource if found; {@code null} otherwise
     */
    T show(int id);

    /**
     * Stores a new resource.
     *
     * @param entity the entity to store
     * @return {@code true} if the resource was successfully stored;
     *         {@code false} otherwise
     */
    boolean store(T entity);

    /**
     * Deletes a resource by its ID.
     *
     * @param id the ID of the resource to delete
     * @return {@code true} if the resource was successfully deleted;
     *         {@code false} otherwise
     */
    boolean destroy(int id);

    /**
     * Updates an existing resource.
     *
     * @param entity the entity containing the updated data
     * @return {@code true} if the resource was successfully updated;
     *         {@code false} otherwise
     */
    boolean update(T entity);

    /**
     * Import accounts by CSV file
     *
     * @param filePath Absolute path of file to import
     */
    void importFromCSV(String filePath);
}