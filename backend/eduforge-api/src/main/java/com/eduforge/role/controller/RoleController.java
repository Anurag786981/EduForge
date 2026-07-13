package com.eduforge.role.controller;

import com.eduforge.role.dto.RoleRequest;
import com.eduforge.role.dto.RoleResponse;
import com.eduforge.role.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;

  @Operation(summary = "Create Role", description = "Creates a new role in the system.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Role created successfully"),
    @ApiResponse(responseCode = "400", description = "Validation failed"),
    @ApiResponse(responseCode = "409", description = "Role already exists")
  })
  @PostMapping
  public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleRequest roleRequest) {

    RoleResponse roleResponse = roleService.createRole(roleRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(roleResponse);
  }

  @Operation(summary = "Get Role By ID", description = "Retrieves Role detail using role Id.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Role Retrieves successfully"),
    @ApiResponse(responseCode = "400", description = "Role Not found"),
    @ApiResponse(responseCode = "409", description = "Unauthorized")
  })
  @GetMapping("/{roleId}")
  public ResponseEntity<RoleResponse> findRoleById(@Valid @PathVariable Long roleId) {
    return ResponseEntity.ok(roleService.getRoleById(roleId));
  }

  @Operation(summary = "Get All Role ", description = "Retrieves All Role")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Role Retrieves successfully"),
    @ApiResponse(responseCode = "400", description = "Role Not found"),
    @ApiResponse(responseCode = "409", description = "Unauthorized")
  })
  @GetMapping
  public ResponseEntity<List<RoleResponse>> getAllRoles() {
    return ResponseEntity.ok(roleService.getAllRoles());
  }

  @Operation(summary = "Update Role", description = "Update existing role role in the system.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Role Updated successfully"),
    @ApiResponse(responseCode = "400", description = "Validation failed"),
    @ApiResponse(responseCode = "409", description = "Role already exists")
  })
  @PutMapping("/{roleId}")
  ResponseEntity<RoleResponse> updateRole(
      @PathVariable Long roleId, @Valid @RequestBody RoleRequest roleRequest) {
    return ResponseEntity.ok(roleService.updateRole(roleId, roleRequest));
  }

  @Operation(summary = "Activate Role", description = "Activates an existing role in the system.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Role activated successfully"),
    @ApiResponse(responseCode = "404", description = "Role not found"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PatchMapping("/{roleId}/activate")
  public ResponseEntity<RoleResponse> activateSchool(@PathVariable Long roleId) {
    return ResponseEntity.ok(roleService.activateRole(roleId));
  }

  @Operation(
      summary = "Deactivate Role",
      description = "Deactivates an existing role in the system.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Role deactivated successfully"),
    @ApiResponse(responseCode = "404", description = "Role not found"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PatchMapping("/{roleId}/deactivate")
  public ResponseEntity<RoleResponse> deActivateSchool(@PathVariable Long roleId) {
    return ResponseEntity.ok(roleService.deActivateRole(roleId));
  }
}
