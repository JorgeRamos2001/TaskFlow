package com.app.services;

import com.app.models.dtos.UserDTO;
import com.app.models.requests.UpdatePasswordRequest;
import com.app.models.requests.UpdateUserRequest;

public interface UserService {
    UserDTO getCurrentUser();
    UserDTO updateCurrentUser(UpdateUserRequest request);
    UserDTO updateCurrentUserPassword(UpdatePasswordRequest request);
    void deleteCurrentUser();
}
