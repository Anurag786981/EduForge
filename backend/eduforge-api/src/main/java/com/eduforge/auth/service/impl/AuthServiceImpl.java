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
import java.util.Optional;

import com.eduforge.role.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  @Override
  public RegisterResponse register(RegisterRequest registerRequest) {

    // Checking Duplicate
    Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());

    //
    if (existingUser.isPresent()) {
      throw new UserAlreadyExistsException(
          "An Account with this email already exists. :" + registerRequest.getEmail());
    }
    User user =
        User.builder()
            .firstName(registerRequest.getFirstName())
            .lastName(registerRequest.getLastName())
            .email(registerRequest.getEmail())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .role(Role.builder().build())
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
            .role(String.valueOf(savedUser.getRole()))
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
        // TODO: Add role-based authorization is implemented
        // .role(user.getRole())
        .token(token)
        .schoolId(null)
        .schoolName(null)
        .active(true)
        .build();
  }
}
