package com.app.repositories;

import com.app.models.entities.Project;
import com.app.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findById(UUID id);
    List<Project> findAllByUser(User user);
    List<Project> findAllByUserAndNameContainingIgnoreCase(User user, String name);
}
