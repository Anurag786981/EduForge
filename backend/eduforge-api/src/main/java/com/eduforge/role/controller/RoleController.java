package com.eduforge.role.controller;

import com.eduforge.role.dto.RoleRequest;
import com.eduforge.role.dto.RoleResponse;
import com.eduforge.role.service.RoleService;
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
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Role Management", description = "APIs for managing system Role")
@SecurityRequirement(name = "Bearer Authentication")
public class RoleController {

  private final RoleService roleService;

  @Operation(summary = "Create Role", description = "Creates a new role in the system.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Resource created successfully"),
    @ApiResponse(responseCode = "400", description = "Validation failed"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied"),
    @ApiResponse(responseCode = "409", description = "Resource already exists")
  })
  @PostMapping
  @PreAuthorize(("hasAuthority('ROLE_CREATE')"))
  public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleRequest roleRequest) {

    RoleResponse roleResponse = roleService.createRole(roleRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(roleResponse);
  }

  @Operation(summary = "Get Role By ID", description = "Retrieves Role detail using role Id.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Resource retrieved successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied"),
    @ApiResponse(responseCode = "404", description = "Resource not found")
  })
  @GetMapping("/{roleId}")
  @PreAuthorize(("hasAuthority('ROLE_VIEW')"))
  public ResponseEntity<RoleResponse> findRoleById(@Valid @PathVariable Long roleId) {
    return ResponseEntity.ok(roleService.getRoleById(roleId));
  }

  @Operation(summary = "Get All Role ", description = "Retrieves All Role")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Resources retrieved successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied")
  })
  @GetMapping
  @PreAuthorize(("hasAuthority('ROLE_VIEW')"))
  public ResponseEntity<List<RoleResponse>> getAllRoles() {
    return ResponseEntity.ok(roleService.getAllRoles());
  }

  @Operation(summary = "Update Role", description = "Update existing role role in the system.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Resource updated successfully"),
    @ApiResponse(responseCode = "400", description = "Validation failed"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied"),
    @ApiResponse(responseCode = "404", description = "Resource not found")
  })
  @PutMapping("/{roleId}")
  @PreAuthorize(("hasAuthority('ROLE_UPDATE')"))
  ResponseEntity<RoleResponse> updateRole(
      @PathVariable Long roleId, @Valid @RequestBody RoleRequest roleRequest) {
    return ResponseEntity.ok(roleService.updateRole(roleId, roleRequest));
  }

  @Operation(summary = "Activate Role", description = "Activates an existing role in the system.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Resource activated successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied"),
    @ApiResponse(responseCode = "404", description = "Resource not found")
  })
  @PatchMapping("/{roleId}/activate")
  @PreAuthorize(("hasAuthority('ROLE_ACTIVATE')"))
  public ResponseEntity<RoleResponse> activateSchool(@PathVariable Long roleId) {
    return ResponseEntity.ok(roleService.activateRole(roleId));
  }

  @Operation(
      summary = "Deactivate Role",
      description = "Deactivates an existing role in the system.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Resource deactivated successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied"),
    @ApiResponse(responseCode = "404", description = "Resource not found")
  })
  @PatchMapping("/{roleId}/deactivate")
  @PreAuthorize(("hasAuthority('ROLE_DEACTIVATE')"))
  public ResponseEntity<RoleResponse> deActivateSchool(@PathVariable Long roleId) {
    return ResponseEntity.ok(roleService.deActivateRole(roleId));
  }
}
