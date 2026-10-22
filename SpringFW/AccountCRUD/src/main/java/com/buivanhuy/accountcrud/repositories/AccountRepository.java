package com.buivanhuy.accountcrud.repositories;

import com.buivanhuy.accountcrud.entities.Account;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Override
    @EntityGraph(attributePaths = {"department", "position"})
    Page<Account> findAll(@Nullable Specification<Account> spec, @NonNull Pageable pageable);

    @EntityGraph(attributePaths = {"department", "position"})
    Optional<Account> findWithDetailsById(Long id);
}
