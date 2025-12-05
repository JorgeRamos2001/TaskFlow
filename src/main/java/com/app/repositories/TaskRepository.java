package com.app.repositories;

import com.app.models.entities.Project;
import com.app.models.entities.Task;
import com.app.models.enums.TaskPriority;
import com.app.models.enums.TaskState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findById(UUID id);
    List<Task> findAllByProject_User_Id(UUID userId);
    List<Task> findAllByProject(Project project);
    List<Task> findAllByProjectAndPriority(Project project, TaskPriority priority);
    List<Task> findAllByProjectAndTitleContainingIgnoreCase(Project project, String title);
    List<Task> findAllByProjectAndState(Project project, TaskState state);
    void deleteAllByProject(Project project);
}
