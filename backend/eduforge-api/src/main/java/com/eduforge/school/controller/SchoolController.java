package com.eduforge.school.controller;

import com.eduforge.school.dto.SchoolRequest;
import com.eduforge.school.dto.SchoolResponse;
import com.eduforge.school.service.SchoolService;
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
@RequestMapping("/api/schools")
@RequiredArgsConstructor
public class SchoolController {

  private final SchoolService schoolService;

  @Operation(summary = "Create School", description = "Creates a new School in the system.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "School created successfully"),
    @ApiResponse(responseCode = "400", description = "Validation failed"),
    @ApiResponse(responseCode = "409", description = "School already exists")
  })
  @PostMapping("/registerSchool")
  public ResponseEntity<SchoolResponse> createSchool(
      @Valid @RequestBody SchoolRequest schoolRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(schoolService.createSchool(schoolRequest));
  }

  @GetMapping("/{schoolId}")
  public ResponseEntity<SchoolResponse> getSchoolById(@PathVariable Long schoolId) {
    return ResponseEntity.ok(schoolService.getSchoolById(schoolId));
  }

  @GetMapping()
  public ResponseEntity<List<SchoolResponse>> getAllSchools() {
    return ResponseEntity.ok(schoolService.getAllSchools());
  }

  @PutMapping("/{schoolId}")
  public ResponseEntity<SchoolResponse> updateSchool(
      @PathVariable Long schoolId, @Valid @RequestBody SchoolRequest schoolRequest) {
    return ResponseEntity.ok(schoolService.updateSchool(schoolId, schoolRequest));
  }

  @Operation(
      summary = "Activate School",
      description = "Activates an existing school in the system.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "School activated successfully"),
    @ApiResponse(responseCode = "404", description = "School not found"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PatchMapping("/{schoolId}/activate")
  public ResponseEntity<SchoolResponse> activateSchool(@PathVariable Long schoolId) {
    return ResponseEntity.ok(schoolService.activateSchool(schoolId));
  }

  @Operation(
      summary = "Deactivate School",
      description = "Deactivates an existing school in the system.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "School deactivated successfully"),
    @ApiResponse(responseCode = "404", description = "school not found"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PatchMapping("/{schoolId}/deactivate")
  public ResponseEntity<SchoolResponse> deActivateSchool(@PathVariable Long schoolId) {
    return ResponseEntity.ok(schoolService.deactivateSchool(schoolId));
  }
}
