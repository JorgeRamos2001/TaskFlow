package com.app.services.implementations;

import com.app.exceptions.InvalidOperationException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.mappers.TaskMapper;
import com.app.models.dtos.TaskDTO;
import com.app.models.entities.Project;
import com.app.models.entities.Task;
import com.app.models.entities.User;
import com.app.models.enums.TaskPriority;
import com.app.models.enums.TaskState;
import com.app.models.requests.CreateTaskRequest;
import com.app.models.requests.UpdateTaskRequest;
import com.app.repositories.ProjectRepository;
import com.app.repositories.TaskRepository;
import com.app.repositories.UserRepository;
import com.app.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskDTO createTask(UUID projectId, CreateTaskRequest request) {
        try {
            TaskPriority priority = TaskPriority.valueOf(request.priority().toUpperCase());
            Project project = validateProjectOwner(projectId);
            Task task = taskMapper.toEntity(request);
            task.setPriority(priority);
            task.setProject(project);
            return taskMapper.toDto(taskRepository.save(task));
        }catch (IllegalArgumentException e){
            throw new InvalidOperationException("Invalid priority. Valid priorities: LOW, MEDIUM, HIGH");
        }
    }

    @Override
    public TaskDTO getTaskById(UUID projectId, UUID taskId) {
        validateProjectOwner(projectId);
        Task task = findTaskAndValidateProject(taskId, projectId);
        return taskMapper.toDto(task);
    }

    @Override
    public List<TaskDTO> getAllTasks(UUID projectId) {
        Project project = validateProjectOwner(projectId);
        return taskRepository.findAllByProject(project).stream().map(taskMapper::toDto).toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByTitle(UUID projectId, String title) {
        Project project = validateProjectOwner(projectId);
        return taskRepository.findAllByProjectAndTitleContainingIgnoreCase(project, title).stream().map(taskMapper::toDto).toList();
    }

    @Override
    @Transactional
    public TaskDTO updateTask(UUID projectId, UUID taskId, UpdateTaskRequest request) {
        validateProjectOwner(projectId);
        Task task = findTaskAndValidateProject(taskId, projectId);
        task.setTitle(request.title());
        task.setDescription(request.description());
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public TaskDTO updateTaskPriority(UUID projectId, UUID taskId, TaskPriority priority) {
        validateProjectOwner(projectId);
        Task task = findTaskAndValidateProject(taskId, projectId);
        task.setPriority(priority);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public TaskDTO updateTaskState(UUID projectId, UUID taskId, TaskState state) {
        validateProjectOwner(projectId);
        Task task = findTaskAndValidateProject(taskId, projectId);
        task.setState(state);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public void deleteTask(UUID projectId, UUID taskId) {
        validateProjectOwner(projectId);
        Task task = findTaskAndValidateProject(taskId, projectId);
        taskRepository.delete(task);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) throw new ResourceNotFoundException("Authenticated user not found");
        final String username = authentication.getName();
        return userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found with the email"));
    }

    private Project validateProjectOwner(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with the id: " + projectId));

        User currentUser = getAuthenticatedUser();

        if (!project.getUser().getId().equals(currentUser.getId())) {
            throw new InvalidOperationException("You are not allowed to access this resource");
        }
        return project;
    }

    private Task findTaskAndValidateProject(UUID taskId, UUID projectId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with the id: " + taskId));

        if (!task.getProject().getId().equals(projectId)) {
            throw new InvalidOperationException("The task does not belong to the specified project");
        }
        return task;
    }
}
