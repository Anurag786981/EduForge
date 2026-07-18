package com.eduforge.auth.service.impl;

import com.eduforge.auth.dto.LoginRequest;
import com.eduforge.auth.dto.LoginResponse;
import com.eduforge.auth.dto.RegisterRequest;
import com.eduforge.auth.dto.RegisterResponse;
import com.eduforge.auth.entity.User;
import com.eduforge.auth.exception.InvalidCredentialsException;
import com.eduforge.auth.exception.UserAlreadyExistsException;
import com.eduforge.auth.repository.UserRepository;
import com.eduforge.auth.security.JwtService;
import com.eduforge.auth.service.AuthService;
import com.eduforge.common.exception.BadRequestException;
import com.eduforge.common.exception.ResourceNotFoundException;
import com.eduforge.role.entity.Role;
import com.eduforge.role.repository.RoleRepository;
import com.eduforge.school.entity.School;
import com.eduforge.school.repository.SchoolRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final SchoolRepository schoolRepository;
  private final RoleRepository roleRepository;

  @Override
  public RegisterResponse register(RegisterRequest registerRequest) {

    // Checking Duplicate
    Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());

    // Null check for every user must be part of school

    if (registerRequest.getSchoolId() == null) {
      throw new BadRequestException("School Id is required.");
    }
    // Checking School with id
    School school =
        schoolRepository
            .findById(registerRequest.getSchoolId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "School not found with id : " + registerRequest.getSchoolId()));

    // fetch Role
    Role role =
        roleRepository
            .findById(registerRequest.getRoleId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Role not found with id : " + registerRequest.getRoleId()));

    // checking email
    if (existingUser.isPresent()) {
      throw new UserAlreadyExistsException(
          "An Account with this email already exists. :" + registerRequest.getEmail());
    }
    // Ensure the selected role is associated with the requested school
    if (role.getSchool() == null || !role.getSchool().getId().equals(school.getId())) {
      throw new BadRequestException("Selected role does not belongs to the selected school.");
    }
    // Build user with the selected role and School
    User user =
        User.builder()
            .firstName(registerRequest.getFirstName())
            .lastName(registerRequest.getLastName())
            .email(registerRequest.getEmail())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .school(school)
            .role(role)
            .build();

    /*
     * Registers a new user.
     */
    User savedUser = userRepository.save(user);

    // Build RegisterResponse
    RegisterResponse registerResponse =
        RegisterResponse.builder()
            .userId(savedUser.getId())
            .firstName(savedUser.getFirstName())
            .lastName(savedUser.getLastName())
            .email(savedUser.getEmail())
            .role(savedUser.getRole().getRoleName())
            .school(savedUser.getSchool().getSchoolName())
            .build();

    return registerResponse;
  }

  @Override
  public LoginResponse login(LoginRequest loginRequest) {

    // Step 1 : Find user by email
    User user =
        userRepository
            .findByEmail(loginRequest.getEmail())
            .orElseThrow(
                () ->
                    new InvalidCredentialsException(
                        "No Account found with email : " + loginRequest.getEmail()));

    // Step 2 : Validate Password
    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      throw new InvalidCredentialsException("Invalid email or password");
    }

    // Step 3: Generate JWT
    String token = jwtService.generateToken(user);

    // Step 4: Build LoginResponse
    return LoginResponse.builder()
        .id(user.getId())
        .firstName(user.getFirstName())
        .lastname(user.getLastName())
        .email(user.getEmail())
        .role(user.getRole().getRoleName())
        .schoolId(user.getSchool() != null ? user.getSchool().getId() : null)
        .schoolName(user.getSchool() != null ? user.getSchool().getSchoolName() : null)
        .token(token)
        .active(true)
        .build();
  }
}
