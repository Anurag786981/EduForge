package com.eduforge.rolepermission.controller;

import com.eduforge.rolepermission.dto.AssignPermissionRequest;
import com.eduforge.rolepermission.dto.RolePermissionResponse;
import com.eduforge.rolepermission.service.RolePermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/role-permissions")
@RequiredArgsConstructor
@Tag(name = "Role Permission", description = "APIs for managing role-permission mappings.")
@SecurityRequirement(name = "Bearer Authentication")
public class RolePermissionController {
  private final RolePermissionService rolePermissionService;

  @Operation(
      summary = "Assign Role permission",
      description = "Assign a new role and permission in the system.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Resource created successfully"),
    @ApiResponse(responseCode = "400", description = "Validation failed"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied"),
    @ApiResponse(responseCode = "409", description = "Resource already exists")
  })
  @PostMapping("/{roleId}/permissions")
  public ResponseEntity<RolePermissionResponse> assignPermissions(
      @PathVariable Long roleId, @Valid @RequestBody AssignPermissionRequest request) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(rolePermissionService.assignPermissions(roleId, request));
  }

  @Operation(
      summary = "Get All Role & Permission ",
      description = "Retrieves All Role and Permission")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Resources retrieved successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied")
  })
  @GetMapping("/{roleId}/permissions")
  public ResponseEntity<RolePermissionResponse> getPermissionsByRole(@PathVariable Long roleId) {

    return ResponseEntity.ok(rolePermissionService.getPermissionsByRole(roleId));
  }

  @Operation(
      summary = "Update Role And Permission",
      description = "Update existing role and Permission in the system.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Resource updated successfully"),
    @ApiResponse(responseCode = "400", description = "Validation failed"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied"),
    @ApiResponse(responseCode = "404", description = "Resource not found")
  })
  @PutMapping("/{roleId}/permissions")
  public ResponseEntity<RolePermissionResponse> updatePermissions(
      @PathVariable Long roleId, @Valid @RequestBody AssignPermissionRequest request) {

    return ResponseEntity.ok(rolePermissionService.updatePermissions(roleId, request));
  }

  @Operation(
      summary = "Remove Permission",
      description = "Removes a permission from the specified role.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Permission removed successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied"),
    @ApiResponse(responseCode = "404", description = "Role, Permission, or Mapping not found")
  })
  @DeleteMapping("/{roleId}/permissions/{permissionId}")
  public ResponseEntity<Void> removePermission(
      @PathVariable Long roleId, @PathVariable Long permissionId) {

    rolePermissionService.removePermission(roleId, permissionId);

    return ResponseEntity.noContent().build();
  }
}
