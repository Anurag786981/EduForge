package com.eduforge.role.service.impl;

import com.eduforge.auth.security.principal.CustomUserPrincipal;
import com.eduforge.common.exception.AccessDeniedException;
import com.eduforge.common.exception.BadRequestException;
import com.eduforge.common.exception.ResourceNotFoundException;
import com.eduforge.role.dto.RoleRequest;
import com.eduforge.role.dto.RoleResponse;
import com.eduforge.role.entity.Role;
import com.eduforge.role.exception.DuplicateRoleException;
import com.eduforge.role.mapper.RoleMapper;
import com.eduforge.role.repository.RoleRepository;
import com.eduforge.role.service.RoleService;
import com.eduforge.school.entity.School;
import com.eduforge.school.repository.SchoolRepository;
import com.eduforge.user.entity.User;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;
  private final RoleMapper roleMapper;
  private final SchoolRepository schoolRepository;

  private static final Set<String> RESERVED_ROLES =
      Set.of("SUPER_ADMIN", "SCHOOL_ADMIN", "PRINCIPAL", "TEACHER", "STUDENT");

  @Override
  public RoleResponse createRole(RoleRequest roleRequest) {

    // get logged-in user
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomUserPrincipal currentUser = (CustomUserPrincipal) authentication.getPrincipal();

    // Check is super Admin
    boolean isSuperAdmin =
        "SUPER_ADMIN".equalsIgnoreCase(currentUser.getUser().getRole().getRoleName());

    // Decide which School Id to use
    Long schoolId;
    if (isSuperAdmin) {
      schoolId = roleRequest.getSchoolId();
    } else {
      schoolId = currentUser.getSchool().getId();
    }

    if ("SUPER_ADMIN".equalsIgnoreCase(roleRequest.getRoleName())) {
      throw new BadRequestException(
          "SUPER_ADMIN is a reserved platform role and cannot be created through this API.");
    }

    // Normalize Role Name
    String roleName = roleRequest.getRoleName().trim().toUpperCase();
    String normalizedRoleName = roleRequest.getRoleName().trim().toUpperCase().replace(" ", "_");

    // Fetch school by id
    School school =
        schoolRepository
            .findById(schoolId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "School not found with id: " + roleRequest.getSchoolId()));

    // Check if role already exists in the school
    if (roleRepository.existsBySchoolAndRoleName(school, roleName)) {
      throw new DuplicateRoleException(
          "Role ' " + roleName + "' Already exists in the selected school.");
    }

    // Mapper+entity
    if (RESERVED_ROLES.contains(normalizedRoleName)) {
      throw new BadRequestException("Role ' " + roleName + "' is a reserved system Role.");
    }
    Role role = roleMapper.toEntity(roleRequest);
    role.setRoleName(normalizedRoleName);
    role.setSchool(school);
    // Repository.save
    Role savedRole = roleRepository.save(role);
    // Mapper-> Response
    return roleMapper.toResponse(savedRole);
  }

  @Override
  public RoleResponse getRoleById(Long roleId) {

    // get logged-in user
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();

    User currentUser = principal.getUser();
    // Check is super Admin
    boolean isSuperAdmin = "SUPER_ADMIN".equalsIgnoreCase(currentUser.getRole().getRoleName());

    Role role =
        roleRepository
            .findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found with id : " + roleId));

    // school validation for admin

    if (!isSuperAdmin && !role.getSchool().getId().equals(currentUser.getSchool().getId())) {
      throw new AccessDeniedException("you are not authorized to access this role.");
    }
    return roleMapper.toResponse(role);
  }

  @Override
  public List<RoleResponse> getAllRoles() {
    // get logged-in user
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();

    User currentUser = principal.getUser();
    // Check is super Admin
    boolean isSuperAdmin = "SUPER_ADMIN".equalsIgnoreCase(currentUser.getRole().getRoleName());

    List<Role> roles;
    if (isSuperAdmin) {
      roles = roleRepository.findAll();
    } else {
      roles = roleRepository.findBySchool(currentUser.getSchool());
    }

    return roleMapper.toResponseList(roles);
  }

  @Override
  public RoleResponse updateRole(Long roleId, RoleRequest roleRequest) {

    // get logged-in user
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();

    User currentUser = principal.getUser();
    // Check is super Admin
    boolean isSuperAdmin = "SUPER_ADMIN".equalsIgnoreCase(currentUser.getRole().getRoleName());

    // fetch the role by id
    Role role =
        roleRepository
            .findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found with id : " + roleId));
    // Normalize Role
    String roleName = roleRequest.getRoleName().trim().toUpperCase().replace(" ", "_");

    if (!isSuperAdmin && !role.getSchool().getId().equals(currentUser.getSchool().getId())) {
      throw new AccessDeniedException("you are not authorized to access this role.");
    }
    // retrieve associated School
    School school = role.getSchool();
    // Check if another role with same name exists in the school
    /*
     * 1-> Teacher
     * 2-> PRINCIPAL
     * if you update 2 with teacher it will fail duplicate assign the new role you have to give.
     * */
    if (roleRepository.existsBySchoolAndRoleNameAndIdNot(school, roleName, roleId)) {
      throw new DuplicateRoleException(
          "Another Role with name '" + roleName + "'already exists in the selected school.");
    }
    if (RESERVED_ROLES.contains(roleName)) {
      throw new BadRequestException("Role ' " + roleName + "' is a reserved system Role.");
    }
    // update the role details

    role.setRoleName(roleName);
    role.setDescription(roleRequest.getDescription());

    Role updatedRole = roleRepository.save(role);
    return roleMapper.toResponse(updatedRole);
  }

  @Override
  public RoleResponse activateRole(Long roleId) {
    return updateRoleStatus(roleId, true);
  }

  @Override
  public RoleResponse deActivateRole(Long roleId) {
    return updateRoleStatus(roleId, false);
  }

  private RoleResponse updateRoleStatus(Long roleId, boolean status) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();

    User loggedInUser = customUserPrincipal.getUser();
    // Check Role Exists
    Role role =
        roleRepository
            .findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found with id : " + roleId));

    if (!"SUPER_ADMIN".equals(loggedInUser.getRole().getRoleName())) {
      if (!role.getSchool().getId().equals(loggedInUser.getSchool().getId())) {
        throw new AccessDeniedException("You are not allowed to modify users from another school.");
      }
    }

    role.setActive(status);

    Role updatedRole = roleRepository.save(role);

    return roleMapper.toResponse(updatedRole);
  }
}
