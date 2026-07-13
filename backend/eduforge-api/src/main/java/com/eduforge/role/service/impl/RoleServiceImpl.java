package com.eduforge.role.service.impl;

import com.eduforge.common.exception.ResourceNotFoundException;
import com.eduforge.role.dto.RoleRequest;
import com.eduforge.role.dto.RoleResponse;
import com.eduforge.role.entity.Role;
import com.eduforge.role.exception.DuplicateRoleException;
import com.eduforge.role.mapper.RoleMapper;
import com.eduforge.role.repository.RoleRepository;
import com.eduforge.role.service.RoleService;
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

  @Override
  public RoleResponse createRole(RoleRequest roleRequest) {

    // Normalize Role Name
    String roleName = roleRequest.getRoleName().trim().toUpperCase();

    // validate Duplicate Role
    if (roleRepository.existsByRoleName(roleName)) {
      throw new DuplicateRoleException("Role Already exists with name " + roleName);
    }
    // Mapper+entity

    Role role = roleMapper.toEntity(roleRequest);
    role.setRoleName(roleName);
    role.setActive(true);
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
  public List<RoleResponse> getAllRoles() {

    List<Role> allRole = roleRepository.findAll();

    return roleMapper.toResponseList(allRole);
  }

  @Override
  public RoleResponse updateRole(Long roleId, RoleRequest roleRequest) {
    // Check Role Exists
    Role role =
        roleRepository
            .findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found with id : " + roleId));
    // Normalize Role
    String roleName = roleRequest.getRoleName().trim().toUpperCase();
    // Check duplicate Excluding current Role
    /*
     * 1-> Teacher
     * 2-> PRINCIPAL
     * if you update 2 with teacher it will fail duplicate assign the new role you have to give.
     * */
    if (roleRepository.existsByRoleNameAndIdNot(roleName, roleId)) {
      throw new DuplicateRoleException("Another Role already exists with this Role name");
    }

    role.setRoleName(roleRequest.getRoleName());
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
