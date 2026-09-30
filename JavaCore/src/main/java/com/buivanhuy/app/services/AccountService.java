package com.buivanhuy.app.services;

import com.buivanhuy.entities.Account;

/**
 * Service interface for managing {@link Account} entities.
 * Extends {@link Manageable} to inherit standard CRUD operations and defines
 * additional account-specific business logic such as user authentication.
 */
public interface AccountService extends Manageable<Account> {

    /**
     * Authenticates an account based on the provided username and password.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user.
     * @return An {@link Account} object representing the authenticated user.
     * @throws IllegalArgumentException if the credentials are null, invalid, or incorrect.
     */
    Account login(String username, String password);
}