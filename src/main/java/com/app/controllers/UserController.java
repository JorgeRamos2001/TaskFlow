package com.app.controllers;

import com.app.models.dtos.UserDTO;
import com.app.models.requests.UpdatePasswordRequest;
import com.app.models.requests.UpdateUserRequest;
import com.app.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
    }

    @PutMapping("/me/update")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UpdateUserRequest request) {
        return new ResponseEntity<>(userService.updateCurrentUser(request), HttpStatus.OK);
    }

    @PutMapping("/me/updatePassword")
    public ResponseEntity<UserDTO> updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
        return new ResponseEntity<>(userService.updateCurrentUserPassword(request), HttpStatus.OK);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCurrentUser() {
        userService.deleteCurrentUser();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
