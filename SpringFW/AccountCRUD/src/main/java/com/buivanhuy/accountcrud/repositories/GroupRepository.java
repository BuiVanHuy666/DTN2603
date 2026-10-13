package com.buivanhuy.accountcrud.repositories;

import com.buivanhuy.accountcrud.entities.Group;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Override
    @EntityGraph(attributePaths = "creator")
    @NullMarked
    List<Group> findAll();

    @EntityGraph(attributePaths = "creator")
    Optional<Group> findWithCreatorById(Long id);

    List<Group> findByCreator_Id(Long creatorId);
}
