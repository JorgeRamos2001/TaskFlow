package com.app.controllers;

import com.app.models.dtos.AuthDTO;
import com.app.models.requests.LoginRequest;
import com.app.models.requests.RegisterRequest;
import com.app.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.loginUser(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.registerUser(registerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
