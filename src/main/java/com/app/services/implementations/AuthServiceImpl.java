package com.app.services.implementations;

import com.app.exceptions.ResourceAlreadyExistException;
import com.app.mappers.UserMapper;
import com.app.models.dtos.AuthDTO;
import com.app.models.entities.User;
import com.app.models.requests.LoginRequest;
import com.app.models.requests.RegisterRequest;
import com.app.repositories.UserRepository;
import com.app.security.jwt.JwtService;
import com.app.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Override
    public AuthDTO loginUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.email());
        final String token = jwtService.generateToken(userDetails);
        return new AuthDTO(token);
    }

    @Override
    @Transactional
    public void registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.email())) throw new ResourceAlreadyExistException("The email is already in use!");
        User user = userMapper.toEntity(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        userRepository.save(user);
    }
}
