package com.app.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequest(
        @NotBlank( message = "Old password is required")
        String oldPassword,
        @NotBlank( message = "New password is required")
        @Size(min = 5, message = "Password must be at least 5 characters")
        String newPassword
) {}
