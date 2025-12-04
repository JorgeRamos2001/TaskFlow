package com.app.models.dtos;

import com.app.models.enums.TaskPriority;
import com.app.models.enums.TaskState;

import java.util.UUID;

public record TaskDTO(
        UUID id,
        String title,
        String description,
        TaskPriority priority,
        TaskState state,
        UUID projectId,
        String projectName
) {}
