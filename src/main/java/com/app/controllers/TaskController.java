package com.app.controllers;

import com.app.models.dtos.TaskDTO;
import com.app.models.enums.TaskPriority;
import com.app.models.enums.TaskState;
import com.app.models.requests.CreateTaskRequest;
import com.app.models.requests.UpdateTaskRequest;
import com.app.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping()
    public ResponseEntity<TaskDTO> createTask(@PathVariable UUID projectId, @Valid @RequestBody CreateTaskRequest request) {
        return new ResponseEntity<>(taskService.createTask(projectId, request), HttpStatus.CREATED);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable UUID projectId, @PathVariable UUID taskId) {
        return new ResponseEntity<>(taskService.getTaskById(projectId, taskId), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<TaskDTO>> getAllTasks(@PathVariable UUID projectId) {
        return new ResponseEntity<>(taskService.getAllTasks(projectId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskDTO>> getAllTasksByTitle(@PathVariable UUID projectId, @RequestParam String title) {
        return new ResponseEntity<>(taskService.getAllTasksByTitle(projectId, title), HttpStatus.OK);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable UUID projectId, @PathVariable UUID taskId, @Valid @RequestBody UpdateTaskRequest request) {
        return new ResponseEntity<>(taskService.updateTask(projectId, taskId, request), HttpStatus.OK);
    }

    @PutMapping("/{taskId}/priority")
    public ResponseEntity<TaskDTO> updatePriority(@PathVariable UUID projectId, @PathVariable UUID taskId, @RequestParam TaskPriority priority) {
        return new ResponseEntity<>(taskService.updateTaskPriority(projectId, taskId, priority), HttpStatus.OK);
    }

    @PutMapping("/{taskId}/state")
    public ResponseEntity<TaskDTO> updateState(@PathVariable UUID projectId, @PathVariable UUID taskId, @RequestParam TaskState state) {
        return new ResponseEntity<>(taskService.updateTaskState(projectId, taskId, state), HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID projectId, @PathVariable UUID taskId) {
        taskService.deleteTask(projectId, taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
