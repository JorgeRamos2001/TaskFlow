package com.app.services.implementations;

import com.app.exceptions.BadRequestException;
import com.app.exceptions.ResourceAlreadyExistException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.mappers.UserMapper;
import com.app.models.dtos.UserDTO;
import com.app.models.entities.User;
import com.app.models.enums.AccountState;
import com.app.models.requests.UpdatePasswordRequest;
import com.app.models.requests.UpdateUserRequest;
import com.app.repositories.UserRepository;
import com.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDTO getCurrentUser() {
        return userMapper.toDTO(getAuthenticatedUser());
    }

    @Override
    @Transactional
    public UserDTO updateCurrentUser(UpdateUserRequest request) {
        User user = getAuthenticatedUser();
        if (!user.getEmail().equals(request.email())) {
            if (userRepository.existsByEmail(request.email())) throw new ResourceAlreadyExistException("The email is already in use!");
        }
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDTO updateCurrentUserPassword(UpdatePasswordRequest request) {
        User user = getAuthenticatedUser();
        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new BadRequestException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteCurrentUser() {
        User user = getAuthenticatedUser();
        user.setAccountState(AccountState.DELETED);
        userRepository.save(user);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) throw new ResourceNotFoundException("Authenticated user not found");
        final String username = authentication.getName();
        return userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found with the email"));
    }
}
