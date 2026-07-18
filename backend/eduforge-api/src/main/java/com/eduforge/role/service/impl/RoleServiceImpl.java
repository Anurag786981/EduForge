package com.eduforge.role.service.impl;

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
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;
  private final RoleMapper roleMapper;
  private final SchoolRepository schoolRepository;

  @Override
  public RoleResponse createRole(RoleRequest roleRequest) {

    if ("SUPER_ADMIN".equalsIgnoreCase(roleRequest.getRoleName())) {
      throw new IllegalArgumentException(
          "SUPER_ADMIN is a reserved platform role and cannot be created through this API.");
    }

    // Normalize Role Name
    String roleName = roleRequest.getRoleName().trim().toUpperCase();

    // Fetch school by id
    School school =
        schoolRepository
            .findById(roleRequest.getSchoolId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "School not found with id: " + roleRequest.getSchoolId()));

    // Check if role already exists in the school
    if (roleRepository.existsBySchoolAndRoleName(school, roleName)) {
      throw new DuplicateRoleException(
          "Role' " + roleName + "' Already exists in the selected school.");
    }
    // Mapper+entity

    Role role = roleMapper.toEntity(roleRequest);
    role.setRoleName(roleRequest.getRoleName());
    role.setSchool(school);
    // Repository.save
    Role savedRole = roleRepository.save(role);
    // Mapper-> Response
    return roleMapper.toResponse(savedRole);
  }

  @Override
  public RoleResponse getRoleById(Long roleId) {

    Role role =
        roleRepository
            .findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found with id : " + roleId));
    return roleMapper.toResponse(role);
  }

  @Override
  public List<RoleResponse> getAllRoles(Long schoolId) {
    // Fetch school by id
    School school =
        schoolRepository
            .findById(schoolId)
            .orElseThrow(
                () -> new ResourceNotFoundException("School not found with id : " + schoolId));

    // retrieve all roles for the school
    List<Role> roles = roleRepository.findBySchool(school);

    return roleMapper.toResponseList(roles);
  }

  @Override
  public RoleResponse updateRole(Long roleId, RoleRequest roleRequest) {
    // fetch the role by id
    Role role =
        roleRepository
            .findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found with id : " + roleId));
    // Normalize Role
    String roleName = roleRequest.getRoleName().trim().toUpperCase();
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
    // Check Role Exists
    Role role =
        roleRepository
            .findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found with id : " + roleId));

    role.setActive(status);

    Role updatedRole = roleRepository.save(role);

    return roleMapper.toResponse(updatedRole);
  }
}
