package com.eduforge.superadmin.controller;

import com.eduforge.superadmin.dto.CreateSuperAdminRequest;
import com.eduforge.superadmin.dto.SuperAdminResponse;
import com.eduforge.superadmin.dto.UpdateAdminStatusRequest;
import com.eduforge.superadmin.dto.UpdateSuperAdminRequest;
import com.eduforge.superadmin.service.SuperAdminService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/super-admins")
@RequiredArgsConstructor
@Tag(name = "Super Admin", description = "APIs for managing platform Super Administrators.")
@SecurityRequirement(name = "Bearer Authentication")
public class SuperAdminController {
  private final SuperAdminService superAdminService;

  @Operation(
      summary = "Create Super Admin",
      description =
          "Creates a new Super Administrator for the EduForge platform. "
              + "This API is accessible only to authenticated Super Administrators.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Super Admin created successfully."),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request or email already exists."),
        @ApiResponse(responseCode = "401", description = "Unauthorized."),
        @ApiResponse(responseCode = "403", description = "Access denied."),
        @ApiResponse(responseCode = "404", description = "SUPER_ADMIN role not found.")
      })
  @PostMapping
  public ResponseEntity<SuperAdminResponse> createSuperAdmin(
      @Valid @RequestBody CreateSuperAdminRequest request) {

    SuperAdminResponse response = superAdminService.createSuperAdmin(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Operation(summary = "Get All Super Admin ", description = "Retrieves All Super Admin")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Resources retrieved successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied")
  })
  @GetMapping
  public ResponseEntity<List<SuperAdminResponse>> getAllSuperAdmins() {
    return ResponseEntity.ok(superAdminService.getAllSuperAdmins());
  }

  @Operation(
      summary = "Get SuperAdmin By ID",
      description = "Retrieves Super Admin detail using Id.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Resource retrieved successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied"),
    @ApiResponse(responseCode = "404", description = "Resource not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<SuperAdminResponse> getSuperAdminById(@PathVariable Long id) {

    return ResponseEntity.ok(superAdminService.getSuperAdminById(id));
  }

  @Operation(
      summary = "Update SuperAdmin",
      description = "Update existing SuperAdmin in the system.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Resource updated successfully"),
    @ApiResponse(responseCode = "400", description = "Validation failed"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied"),
    @ApiResponse(responseCode = "404", description = "Resource not found")
  })
  @PutMapping("/{id}")
  public ResponseEntity<SuperAdminResponse> updateSuperAdmin(
      @PathVariable Long id, @Valid @RequestBody UpdateSuperAdminRequest request) {

    return ResponseEntity.ok(superAdminService.updateSuperAdmin(id, request));
  }

  @Operation(
      summary = "Activate Admin",
      description = "Activates an existing school in the system.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Resource activated successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied"),
    @ApiResponse(responseCode = "404", description = "Resource not found")
  })
  @PatchMapping("/{id}/status")
  public ResponseEntity<SuperAdminResponse> updateStatus(
      @PathVariable Long id, @Valid @RequestBody UpdateAdminStatusRequest request) {

    return ResponseEntity.ok(superAdminService.updateStatus(id, request));
  }
}
