package com.eduforge.school.controller;

import com.eduforge.school.dto.SchoolRequest;
import com.eduforge.school.dto.SchoolResponse;
import com.eduforge.school.service.SchoolService;
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

  /**
   * Registers a new School after validating the request.
   *
   * @param schoolRequest School Creation details.
   * @return Register School information.
   */
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

  @PostMapping("/{schoolId}")
  public ResponseEntity<SchoolResponse> updateSchool(
      @PathVariable Long schoolId, @Valid @RequestBody SchoolRequest schoolRequest) {
    return ResponseEntity.ok(schoolService.updateSchool(schoolId, schoolRequest));
  }

  @PatchMapping("/{schoolId}/activate")
  public ResponseEntity<SchoolResponse> activateSchool(@PathVariable Long schoolId) {
    return ResponseEntity.ok(schoolService.activateSchool(schoolId));
  }

  @PatchMapping("/{schoolId}/deactivate")
  public ResponseEntity<SchoolResponse> deActivateSchool(@PathVariable Long schoolId) {
    return ResponseEntity.ok(schoolService.deactivateSchool(schoolId));
  }
}
