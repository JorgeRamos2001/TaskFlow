package com.app.models.requests;

import jakarta.validation.constraints.NotBlank;

public record UpdateTaskRequest(
        @NotBlank( message = "Title is required")
        String title,
        @NotBlank( message = "Description is required")
        String description
) {}
