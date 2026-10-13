package com.buivanhuy.accountcrud.repositories;

import com.buivanhuy.accountcrud.entities.Account;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Override
    @EntityGraph(attributePaths = {"department", "position"})
    @NullMarked
    List<Account> findAll();

    @EntityGraph(attributePaths = {"department", "position"})
    Optional<Account> findWithDetailsById(Long id);
}
