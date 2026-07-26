package com.eduforge.school.service.impl;

import com.eduforge.auth.security.principal.CustomUserPrincipal;
import com.eduforge.common.exception.AccessDeniedException;
import com.eduforge.common.exception.ResourceNotFoundException;
import com.eduforge.permission.entity.Permission;
import com.eduforge.permission.repository.PermissionRepository;
import com.eduforge.role.entity.Role;
import com.eduforge.role.repository.RoleRepository;
import com.eduforge.rolepermission.entity.RolePermission;
import com.eduforge.rolepermission.repository.RolePermissionRepository;
import com.eduforge.school.dto.SchoolRequest;
import com.eduforge.school.dto.SchoolResponse;
import com.eduforge.school.entity.School;
import com.eduforge.school.exception.DuplicateDiseCodeException;
import com.eduforge.school.mapper.SchoolMapper;
import com.eduforge.school.repository.SchoolRepository;
import com.eduforge.school.service.SchoolService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

  private final SchoolRepository schoolRepository;

  private final SchoolMapper schoolMapper;

  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;
  private final RolePermissionRepository rolePermissionRepository;

  @Override
  @Transactional
  public SchoolResponse createSchool(SchoolRequest schoolRequest) {

    if (schoolRepository.existsByDiseCode(schoolRequest.getDiseCode())) {
      throw new DuplicateDiseCodeException("School Already exist with this DISE code");
    }

    School school =
        School.builder()
            .schoolName(schoolRequest.getSchoolName())
            .diseCode(schoolRequest.getDiseCode())
            .email(schoolRequest.getEmail())
            .phoneNumber(schoolRequest.getPhoneNumber())
            .address(schoolRequest.getAddress())
            .build();

    School savedSchool = schoolRepository.save(school);

    // Automated Role and permission assignment
    createDefaultRoles(savedSchool);
    assignDefaultPermissions(savedSchool);
    return schoolMapper.mapToResponse(savedSchool);
  }

  @Override
  public SchoolResponse updateSchool(Long schoolId, SchoolRequest schoolRequest) {

    School school =
        schoolRepository
            .findById(schoolId)
            .orElseThrow(
                () -> new ResourceNotFoundException("School Not Found with Given id " + schoolId));

    if (schoolRepository.existsByDiseCodeAndIdNot(schoolRequest.getDiseCode(), schoolId)) {
      throw new DuplicateDiseCodeException("Another School already exists with this Dise Code");
    }

    school.setSchoolName(schoolRequest.getSchoolName());
    school.setDiseCode(schoolRequest.getDiseCode());
    school.setEmail(schoolRequest.getEmail());
    school.setPhoneNumber(school.getPhoneNumber());
    school.setAddress(schoolRequest.getAddress());

    School upadtedSchool = schoolRepository.save(school);
    return schoolMapper.mapToResponse(upadtedSchool);
  }

  @Override
  public SchoolResponse getSchoolById(Long schoolId) {

    School school =
        schoolRepository
            .findById(schoolId)
            .orElseThrow(
                () -> new ResourceNotFoundException("School not found with id : " + schoolId));
    return schoolMapper.mapToResponse(school);
  }

  @Override
  public List<SchoolResponse> getAllSchools() {
    List<School> schools = schoolRepository.findAll();
    return schoolMapper.toResponseList(schools);
  }

  @Override
  public SchoolResponse activateSchool(Long schoolId) {
    return updateSchoolStatus(schoolId, true);
  }

  @Override
  public SchoolResponse deactivateSchool(Long schoolId) {
    return updateSchoolStatus(schoolId, false);
  }

  @Override
  public SchoolResponse getMySchool() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();

    if (principal.getSchool() == null) {
      throw new AccessDeniedException("No Such associated school with logged in user");
    }

    Long schoolId = principal.getSchool().getId();

    School school =
        schoolRepository
            .findById(schoolId)
            .orElseThrow(() -> new ResourceNotFoundException("School not found"));
    return schoolMapper.mapToResponse(school);
  }

  private SchoolResponse updateSchoolStatus(Long schoolId, Boolean status) {
    School school =
        schoolRepository
            .findById(schoolId)
            .orElseThrow(
                () -> new ResourceNotFoundException("School Not found with ID " + schoolId));

    school.setActive(status);

    School upadtedSchool = schoolRepository.save(school);
    return schoolMapper.mapToResponse(upadtedSchool);
  }

  private void createDefaultRoles(School school) {
    createRole(school, "SCHOOL_ADMIN", "Manages users,roles and day to day school administration");
    createRole(school, "PRINCIPAL", "Principal of the school.");
    createRole(school, "TEACHER", "Teaching staff responsible for academic activities.");
    createRole(school, "STUDENT", "Student role with limited access.");
  }

  private void createRole(School school, String roleName, String description) {
    Role role = new Role();

    role.setRoleName(roleName);
    role.setSchool(school);
    role.setActive(true);
    role.setDescription(description);

    roleRepository.save(role);
  }

  private void assignDefaultPermissions(School school) {

    Role schoolAdminRole =
        roleRepository
            .findBySchoolAndRoleName(school, "SCHOOL_ADMIN")
            .orElseThrow(() -> new ResourceNotFoundException("SCHOOL_ADMIN role not found."));

    List<String> permissions =
        List.of(
            "USER_CREATE",
            "USER_UPDATE",
            "USER_VIEW",
            "USER_ACTIVATE",
            "USER_DEACTIVATE",
            "ROLE_CREATE",
            "ROLE_UPDATE",
            "ROLE_VIEW",
            "ROLE_ACTIVATE",
            "ROLE_DEACTIVATE");

    for (String permissionName : permissions) {
      Permission permission =
          permissionRepository
              .findByPermissionName(permissionName)
              .orElseThrow(
                  () -> new ResourceNotFoundException("Permission Not found " + permissionName));

      RolePermission rolePermission = new RolePermission();

      rolePermission.setRole(schoolAdminRole);
      rolePermission.setPermission(permission);

      rolePermissionRepository.save(rolePermission);
    }
  }
}
