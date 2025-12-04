package com.app.models.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectDTO(
        UUID id,
        String name,
        String description,
        LocalDateTime createdAt
) {}
