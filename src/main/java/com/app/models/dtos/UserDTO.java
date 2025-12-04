package com.app.models.dtos;

import com.app.models.enums.AccountState;

import java.util.UUID;

public record UserDTO(
        UUID id,
        String firstName,
        String lastName,
        String email,
        AccountState accountState
) {}
