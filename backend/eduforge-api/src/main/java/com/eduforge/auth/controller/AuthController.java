package com.eduforge.auth.controller;

import com.eduforge.auth.dto.LoginRequest;
import com.eduforge.auth.dto.LoginResponse;
import com.eduforge.auth.dto.RegisterRequest;
import com.eduforge.auth.dto.RegisterResponse;
import com.eduforge.auth.service.AuthService;
import com.eduforge.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Exposes authentication APIs for user registration and login. */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  /**
   * Registers a new user after validating the request.
   *
   * @param registerRequest Registration details.
   * @return Registered user information.
   */
  @PostMapping("/register")
  public ResponseEntity<ApiResponse<RegisterResponse>> register(
      @Valid @RequestBody RegisterRequest registerRequest) {

    RegisterResponse registerResponse = authService.register(registerRequest);

    // Build and return a standardized successful Register response.
    ApiResponse<RegisterResponse> apiResponse =
        ApiResponse.<RegisterResponse>builder()
            .success(true)
            .message("User registered successfully")
            .data(registerResponse)
            .build();
    return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
  }

  /**
   * Authenticates a user and returns login details with a JWT token.
   *
   * @param loginRequest User login credentials.
   * @return Authenticated user information and JWT token.
   */
  @PostMapping("/login")
  public ResponseEntity<ApiResponse<LoginResponse>> login(
      @Valid @RequestBody LoginRequest loginRequest) {

    LoginResponse loginResponse = authService.login(loginRequest);

    // Build and return a standardized successful login response.
    ApiResponse<LoginResponse> apiResponse =
        ApiResponse.<LoginResponse>builder()
            .success(true)
            .message("Login successful")
            .data(loginResponse)
            .build();

    return ResponseEntity.ok(apiResponse);
  }
}
