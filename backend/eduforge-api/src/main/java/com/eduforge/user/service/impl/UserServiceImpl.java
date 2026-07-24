package com.eduforge.user.service.impl;

import com.eduforge.common.exception.BadRequestException;
import com.eduforge.common.exception.ResourceNotFoundException;
import com.eduforge.role.entity.Role;
import com.eduforge.role.repository.RoleRepository;
import com.eduforge.school.entity.School;
import com.eduforge.school.repository.SchoolRepository;
import com.eduforge.user.dto.CreateUserRequest;
import com.eduforge.user.dto.UserResponse;
import com.eduforge.user.entity.User;
import com.eduforge.user.exception.UserAlreadyExistsException;
import com.eduforge.user.mapper.UserMapper;
import com.eduforge.user.repository.UserRepository;
import com.eduforge.user.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final SchoolRepository schoolRepository;
  private final RoleRepository roleRepository;
  private final UserMapper userMapper;

  @Override
  public UserResponse createUser(CreateUserRequest userRequest) {

    // Checking Duplicate
    Optional<User> existingUser = userRepository.findByEmail(userRequest.getEmail());

    // Null check for every user must be part of school

    if (userRequest.getSchoolId() == null) {
      throw new BadRequestException("School Id is required.");
    }

    if (userRequest.getRoleId() == null) {
      throw new BadRequestException("Role Id is required.");
    }
    // Checking School with id
    School school =
        schoolRepository
            .findById(userRequest.getSchoolId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "School not found with id : " + userRequest.getSchoolId()));

    // fetch Role
    Role role =
        roleRepository
            .findById(userRequest.getRoleId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Role not found with id : " + userRequest.getRoleId()));

    // checking email
    if (existingUser.isPresent()) {
      throw new UserAlreadyExistsException(
          "An Account with this email already exists. :" + userRequest.getEmail());
    }
    // Ensure the selected role is associated with the requested school
    if (role.getSchool() == null || !role.getSchool().getId().equals(school.getId())) {
      throw new BadRequestException("Selected role does not belongs to the selected school.");
    }
    // Ensure School have only one admin at a time

    if ("SCHOOL_ADMIN".equals(role.getRoleName())
        && userRepository.existsBySchoolIdAndRoleRoleNameAndActiveTrue(
            school.getId(), "SCHOOL_ADMIN")) {
      throw new BadRequestException("School already has an active School Admin.");
    }
    // Build user with the selected role and School
    User user =
        User.builder()
            .firstName(userRequest.getFirstName())
            .lastName(userRequest.getLastName())
            .email(userRequest.getEmail())
            .password(passwordEncoder.encode(userRequest.getPassword()))
            .school(school)
            .role(role)
            .accountLocked(false)
            .failedLoginAttempts(0)
            .passwordChangeRequired(true)
            .build();

    /*
     * Registers a new user.
     */
    User savedUser = userRepository.save(user);

    // Build RegisterResponse
    return userMapper.mapToResponse(savedUser);
  }

  @Override
  public List<UserResponse> getAllUsers() {
    List<User> allUsers = userRepository.findAll();
    return userMapper.toResponseList(allUsers);
  }

  @Override
  public UserResponse getUserById(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User Not found with ID " + userId));

    return userMapper.mapToResponse(user);
  }

  @Override
  public UserResponse activateUser(Long userId) {
    return updateUserStatus(userId, true);
  }

  @Override
  public UserResponse deactivateUser(Long userId) {
    return updateUserStatus(userId, false);
  }

  @Override
  public UserResponse updateUser(Long userId, CreateUserRequest createUserRequest) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User Not found with ID " + userId));

    Optional<User> existingUser = userRepository.findByEmail(createUserRequest.getEmail());
    // fetch Role
    Role role =
        roleRepository
            .findById(createUserRequest.getRoleId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Role not found with id : " + createUserRequest.getRoleId()));
    // Checking School with id
    School school =
        schoolRepository
            .findById(createUserRequest.getSchoolId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "School not found with id : " + createUserRequest.getSchoolId()));

    // checking email
    if (existingUser.isPresent()) {
      throw new UserAlreadyExistsException(
          "An Account with this email already exists. :" + createUserRequest.getEmail());
    }
    // Ensure the selected role is associated with the requested school
    if (role.getSchool() == null || !role.getSchool().getId().equals(school.getId())) {
      throw new BadRequestException("Selected role does not belongs to the selected school.");
    }

    // Ensure School have only one admin at a time

    if ("SCHOOL_ADMIN".equals(role.getRoleName())
        && userRepository.existsBySchoolIdAndRoleRoleNameAndActiveTrueAndIdNot(
            school.getId(), "SCHOOL_ADMIN", userId)) {
      throw new BadRequestException("School already has an active School Admin.");
    }

    // Build user with the selected role and School

    User.builder()
        .firstName(createUserRequest.getFirstName())
        .lastName(createUserRequest.getLastName())
        .email(createUserRequest.getEmail())
        .password(passwordEncoder.encode(createUserRequest.getPassword()))
        .school(school)
        .role(role)
        .accountLocked(false)
        .failedLoginAttempts(0)
        .passwordChangeRequired(true)
        .build();

    /*
     * Registers a new user.
     */
    User savedUser = userRepository.save(user);

    // Build RegisterResponse
    return userMapper.mapToResponse(savedUser);
  }

  private UserResponse updateUserStatus(Long userId, Boolean status) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User Not found with ID " + userId));

    user.setActive(status);

    User updatedUser = userRepository.save(user);
    return userMapper.mapToResponse(updatedUser);
  }
}
