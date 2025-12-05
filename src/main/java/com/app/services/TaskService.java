package com.app.services;

import com.app.models.dtos.TaskDTO;
import com.app.models.enums.TaskPriority;
import com.app.models.enums.TaskState;
import com.app.models.requests.CreateTaskRequest;
import com.app.models.requests.UpdateTaskRequest;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    TaskDTO createTask(UUID projectId, CreateTaskRequest request);
    TaskDTO getTaskById(UUID projectId, UUID taskId);
    List<TaskDTO> getAllTasks(UUID projectId);
    List<TaskDTO> getAllTasksByTitle(UUID projectId, String title);
    TaskDTO updateTask(UUID projectId, UUID taskId, UpdateTaskRequest request);
    TaskDTO updateTaskPriority(UUID projectId, UUID taskId, TaskPriority priority);
    TaskDTO updateTaskState(UUID projectId, UUID taskId, TaskState state);
    void deleteTask(UUID projectId, UUID taskId);
}
