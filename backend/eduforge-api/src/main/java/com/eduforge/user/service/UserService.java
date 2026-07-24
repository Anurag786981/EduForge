package com.eduforge.user.service;

import com.eduforge.user.dto.CreateUserRequest;
import com.eduforge.user.dto.UserResponse;
import java.util.List;

public interface UserService {

  UserResponse createUser(CreateUserRequest userRequest);

  List<UserResponse> getAllUsers();

  UserResponse getUserById(Long userId);

  UserResponse activateUser(Long userId);

  UserResponse deactivateUser(Long userId);

  UserResponse updateUser(Long userId, CreateUserRequest createUserRequest);
}
