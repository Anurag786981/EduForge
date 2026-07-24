package com.eduforge.user.controller;

import com.eduforge.common.dto.ApiResponse;
import com.eduforge.user.dto.CreateUserRequest;
import com.eduforge.user.dto.UserResponse;
import com.eduforge.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing the User Creation")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

  private final UserService userService;

  @Operation(summary = "Create User", description = "Create a new user in the system.")
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "201",
        description = "User Created successfully"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "400",
        description = "Validation failed"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "409",
        description = "User already exists")
  })
  @PostMapping()
  @PreAuthorize("hasRole('SUPER_ADMIN')")
  public ResponseEntity<ApiResponse<UserResponse>> register(
      @Valid @RequestBody CreateUserRequest registerRequest) {

    UserResponse registerResponse = userService.createUser(registerRequest);

    // Build and return a standardized successful Register response.
    ApiResponse<UserResponse> apiResponse =
        ApiResponse.<UserResponse>builder()
            .success(true)
            .message("User registered successfully")
            .data(registerResponse)
            .build();
    return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
  }

  @Operation(summary = "Get User By ID", description = "Retrieves User detail using user Id.")
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Resource retrieved successfully"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "401",
        description = "Unauthorized"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "403",
        description = "Access denied"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "404",
        description = "Resource not found")
  })
  @GetMapping("/{userId}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
    return ResponseEntity.ok(userService.getUserById(userId));
  }

  @Operation(summary = "Get All User ", description = "Retrieves All User")
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Resources retrieved successfully"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "401",
        description = "Unauthorized"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "403",
        description = "Access denied")
  })
  @GetMapping()
  public ResponseEntity<List<UserResponse>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @Operation(summary = "Update User", description = "Update existing user in the system.")
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Resource updated successfully"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "400",
        description = "Validation failed"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "401",
        description = "Unauthorized"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "403",
        description = "Access denied"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "404",
        description = "Resource not found")
  })
  @PutMapping("/{userId}")
  public ResponseEntity<UserResponse> updateUser(
      @PathVariable Long userId, @Valid @RequestBody CreateUserRequest userRequest) {
    return ResponseEntity.ok(userService.updateUser(userId, userRequest));
  }

  @Operation(summary = "Activate User", description = "Activates an existing user in the system.")
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Resource activated successfully"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "401",
        description = "Unauthorized"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "403",
        description = "Access denied"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "404",
        description = "Resource not found")
  })
  @PatchMapping("/{userId}/activate")
  public ResponseEntity<UserResponse> activateUser(@PathVariable Long userId) {
    return ResponseEntity.ok(userService.activateUser(userId));
  }

  @Operation(
      summary = "Deactivate User",
      description = "Deactivates an existing user in the system.")
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Resource deactivated successfully"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "401",
        description = "Unauthorized"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "403",
        description = "Access denied"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "404",
        description = "Resource not found")
  })
  @PatchMapping("/{userId}/deactivate")
  public ResponseEntity<UserResponse> deActivateSchool(@PathVariable Long userId) {
    return ResponseEntity.ok(userService.deactivateUser(userId));
  }
}
