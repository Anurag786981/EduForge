package com.eduforge.permission.controller;

import com.eduforge.permission.dto.PermissionRequest;
import com.eduforge.permission.dto.PermissionResponse;
import com.eduforge.permission.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@Tag(name = "Permission Management", description = "APIs for managing system permissions")
@SecurityRequirement(name = "Bearer Authentication")
public class PermissionController {

  private final PermissionService permissionService;

  @Operation(summary = "Create Permission", description = "Creates a new permission in the system.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Resource created successfully"),
    @ApiResponse(responseCode = "400", description = "Validation failed"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied"),
    @ApiResponse(responseCode = "409", description = "Resource already exists")
  })
  @PostMapping
  @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('SCHOOL_ADMIN') ")
  public ResponseEntity<PermissionResponse> createPermission(
      @Valid @RequestBody PermissionRequest permissionRequest) {

    PermissionResponse permissionResponse = permissionService.createPermission(permissionRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(permissionResponse);
  }

  @Operation(
      summary = "Get Permission By ID",
      description = "Retrieves permission details using permission ID.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Resource retrieved successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied"),
    @ApiResponse(responseCode = "404", description = "Resource not found")
  })
  @GetMapping("/{permissionId}")
  @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('SCHOOL_ADMIN') ")
  public ResponseEntity<PermissionResponse> getPermissionById(@PathVariable Long permissionId) {

    PermissionResponse permissionResponse = permissionService.getPermissionById(permissionId);

    return ResponseEntity.ok(permissionResponse);
  }

  @Operation(summary = "Get All Permissions", description = "Retrieves all permissions.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Resources retrieved successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied")
  })
  @GetMapping
  @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('SCHOOL_ADMIN') ")
  public ResponseEntity<List<PermissionResponse>> getAllPermissions() {

    return ResponseEntity.ok(permissionService.getAllPermissions());
  }

  @Operation(summary = "Update Permission", description = "Updates permission description.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Resource updated successfully"),
    @ApiResponse(responseCode = "400", description = "Validation failed"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied"),
    @ApiResponse(responseCode = "404", description = "Resource not found")
  })
  @PutMapping("/{permissionId}")
  @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('SCHOOL_ADMIN') ")
  public ResponseEntity<PermissionResponse> updatePermissionDescription(
      @PathVariable Long permissionId, @Valid @RequestBody PermissionRequest permissionRequest) {

    PermissionResponse permissionResponse =
        permissionService.updatePermissionDescription(permissionId, permissionRequest);

    return ResponseEntity.ok(permissionResponse);
  }

  @Operation(summary = "Activate Permission", description = "Activates a permission.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Resource activated successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied"),
    @ApiResponse(responseCode = "404", description = "Resource not found")
  })
  @PatchMapping("/{permissionId}/activate")
  @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('SCHOOL_ADMIN') ")
  public ResponseEntity<PermissionResponse> activatePermission(@PathVariable Long permissionId) {

    return ResponseEntity.ok(permissionService.activatePermission(permissionId));
  }

  @Operation(summary = "Deactivate Permission", description = "Deactivates a permission.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Resource deactivated successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied"),
    @ApiResponse(responseCode = "404", description = "Resource not found")
  })
  @PatchMapping("/{permissionId}/deactivate")
  @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('SCHOOL_ADMIN') ")
  public ResponseEntity<PermissionResponse> deactivatePermission(@PathVariable Long permissionId) {

    return ResponseEntity.ok(permissionService.deactivatePermission(permissionId));
  }

  @Operation(
      summary = "Get My permission ",
      description = "Retrieves all permissions of logged in user.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Resources retrieved successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied")
  })
  @GetMapping("/my-permission")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<PermissionResponse>> getMyPerMission() {

    return ResponseEntity.ok(permissionService.getMyPermission());
  }
}
