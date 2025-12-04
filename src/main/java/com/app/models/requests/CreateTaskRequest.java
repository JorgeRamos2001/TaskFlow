package com.app.models.requests;

import com.app.models.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateTaskRequest(
        @NotBlank( message = "Title is required")
        String title,
        @NotBlank( message = "Description is required")
        String description,
        @NotBlank( message = "Priority is required")
        TaskPriority priority,
        @NotNull( message = "Project Id is required")
        UUID projectId
) {}
