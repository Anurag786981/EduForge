package com.eduforge.auth.controller;

import com.eduforge.auth.dto.LoginRequest;
import com.eduforge.auth.dto.LoginResponse;
import com.eduforge.auth.dto.RegisterRequest;
import com.eduforge.auth.dto.RegisterResponse;
import com.eduforge.auth.service.AuthService;
import com.eduforge.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication Management", description = "APIs for managing the Login and Register")
@SecurityRequirement(name = "Bearer Authentication")
public class AuthController {

  private final AuthService authService;

  @Operation(summary = "Register User", description = "Registers a new user in the system.")
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "201",
        description = "User registered successfully"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "400",
        description = "Validation failed"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "409",
        description = "User already exists")
  })
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

  @Operation(
      summary = "Login User",
      description = "Authenticates the user and returns a JWT token.")
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Login successful"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "400",
        description = "Validation failed"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "401",
        description = "Invalid username or password")
  })
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
