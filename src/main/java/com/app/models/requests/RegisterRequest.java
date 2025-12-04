package com.app.models.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank( message = "First name is required")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,
        @NotBlank( message = "Last name is required")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName,
        @NotBlank( message = "Email is required")
        @Email( message = "Invalid email")
        String email,
        @NotBlank( message = "Password is required")
        @Size(min = 5, message = "Password must be at least 5 characters")
        String password
) {
}
