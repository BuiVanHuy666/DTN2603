package com.buivanhuy.accountcrud.services;

import com.buivanhuy.accountcrud.dto.MemberResponse;

import java.util.List;

/**
 * Generic contract defining foundational CRUD (Create, Read, Update, Delete) operations
 * for domain entities.
 * <p>
 * Input entity instances reaching this service contract are assumed to have already
 * passed format and schema constraint validations at the controller layer (e.g., via {@code @Valid}).
 * Implementations focus on business rules, consistency guarantees, and persistence logic.
 * </p>
 *
 * @param <ID> the type of the entity identifier (typically {@link Long} or {@link String})
 * @param <E>  the type of the domain entity managed by this service
 */
public interface CRUDEntityService<ID, E> {

    /**
     * Creates and persists a new domain entity using the pre-validated input entity.
     *
     * @param entity the pre-validated domain entity to create; must not be {@code null}
     * @return the persisted domain entity
     * @throws RuntimeException if a business constraint is violated (e.g., duplicate unique key)
     */
    E create(E entity);

    /**
     * Retrieves all available domain entities.
     *
     * @return a {@link List} of domain entities, or an empty list if no entities are found
     */
    List<E> findAll();

    /**
     * Retrieves a domain entity by its unique identifier.
     *
     * @param id the unique identifier of the target entity; must not be {@code null}
     * @return the domain entity matching the specified ID
     * @throws RuntimeException (e.g., ResourceNotFoundException) if no entity exists with the given ID
     */
    E findById(ID id);

    /**
     * Updates an existing domain entity identified by its unique ID using the pre-validated input entity.
     *
     * @param id     the unique identifier of the entity to update; must not be {@code null}
     * @param entity the pre-validated domain entity containing updated field values; must not be {@code null}
     * @return the updated domain entity
     * @throws RuntimeException if the target entity does not exist or a business rule fails
     */
    E update(ID id, E entity);

    /**
     * Deletes the domain entity identified by the specified ID.
     *
     * @param id the unique identifier of the entity to delete; must not be {@code null}
     * @throws RuntimeException if the entity cannot be found or is locked by foreign key constraints
     */
    void delete(ID id);

    /**
     * Checks whether a domain entity exists with the given identifier.
     *
     * @param id the unique identifier to verify; must not be {@code null}
     * @return {@code true} if an entity with the specified ID exists, {@code false} otherwise
     */
    boolean existsById(ID id);

    List<MemberResponse> getMembers(Long id);
}