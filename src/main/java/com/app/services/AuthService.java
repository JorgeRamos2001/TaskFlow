package com.app.services;

import com.app.models.dtos.AuthDTO;
import com.app.models.requests.LoginRequest;
import com.app.models.requests.RegisterRequest;

public interface AuthService {
    AuthDTO loginUser(LoginRequest loginRequest);
    void registerUser(RegisterRequest registerRequest);
}
