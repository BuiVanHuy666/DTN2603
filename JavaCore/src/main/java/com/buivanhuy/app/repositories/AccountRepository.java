package com.buivanhuy.app.repositories;

import com.buivanhuy.entities.Account;

/**
 * Repository interface handling specific data access operations for the {@link Account} entity.
 * Extends {@link ResourceRepository} to reuse basic CRUD operations.
 */
public interface AccountRepository extends ResourceRepository<Account> {
    /**
     * Finds and retrieves account information based on the provided username and password.
     * This method is primarily used to authenticate users during the login process.
     *
     * @param username The username of the account to be authenticated.
     * @param password The corresponding password of the account.
     * @return An {@link Account} object containing user information if the credentials are valid.
     *         Returns {@code null} if no matching account is found or if the password does not match.
     */
    Account findByUsernameAndPassword(String username, String password);
}