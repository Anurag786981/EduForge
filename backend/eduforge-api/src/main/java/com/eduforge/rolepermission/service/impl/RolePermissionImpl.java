package com.eduforge.rolepermission.service.impl;

import com.eduforge.common.exception.ResourceNotFoundException;
import com.eduforge.permission.dto.PermissionResponse;
import com.eduforge.permission.entity.Permission;
import com.eduforge.permission.mapper.PermissionMapper;
import com.eduforge.permission.repository.PermissionRepository;
import com.eduforge.role.entity.Role;
import com.eduforge.role.repository.RoleRepository;
import com.eduforge.rolepermission.dto.AssignPermissionRequest;
import com.eduforge.rolepermission.dto.RolePermissionResponse;
import com.eduforge.rolepermission.entity.RolePermission;
import com.eduforge.rolepermission.exception.DuplicateRolePermissionException;
import com.eduforge.rolepermission.repository.RolePermissionRepository;
import com.eduforge.rolepermission.service.RolePermissionService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolePermissionImpl implements RolePermissionService {

  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;
  private final RolePermissionRepository rolePermissionRepository;
  private final PermissionMapper permissionMapper;

  @Override
  @Transactional
  public RolePermissionResponse assignPermissions(Long roleId, AssignPermissionRequest request) {

    // fetch the role from the database throw the exception

    Role role =
        roleRepository
            .findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found wit id : " + roleId));

    // List to store the assigned permission names to the response
    List<String> assignedPermissions = new ArrayList<>();
    List<PermissionResponse> permissionResponsesList = new ArrayList<>();

    // Iterate through each permission ids received in the request
    for (Long permissionId : request.getPermissionsIds()) {
      // Fetch the permission from the database.
      Permission permission =
          permissionRepository
              .findById(permissionId)
              .orElseThrow(
                  (() -> new ResourceNotFoundException("Permission Not found : " + permissionId)));
      // Check Whether role permissio mapping already exists
      if (rolePermissionRepository.existsByRoleAndPermission(role, permission)) {
        throw new DuplicateRolePermissionException(
            "Permission '"
                + permission.getPermissionName()
                + "' is already assigned to role'"
                + role.getRoleName()
                + "'.");
      }
      // Create a new RolePermission mapping.
      RolePermission rolePermission =
          RolePermission.builder().role(role).permission(permission).build();
      // Save the mapping in the database.
      rolePermissionRepository.save(rolePermission);

      // Store the permission name for the response.

      PermissionResponse permissionRes = permissionMapper.toResponse(permission);
      permissionResponsesList.add(permissionRes);
    }
    // Prepare the response DTO.
    RolePermissionResponse response =
        RolePermissionResponse.builder()
            .roleId(role.getId())
            .roleName(role.getRoleName())
            .permissions(permissionResponsesList)
            .build();

    return response;
  }

  @Override
  public RolePermissionResponse getPermissionsByRole(Long roleId) {

    // fetch the  Role by id
    Role role =
        roleRepository
            .findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found wit id : " + roleId));
    // Retrieve all the permissions assigned to the role.
    List<RolePermission> rolePermissions = rolePermissionRepository.findByRole(role);
    // Stored mapped permission responses
    List<PermissionResponse> permissionResponses = new ArrayList<>();
    // convert each permission entity to response DTO.
    for (RolePermission rolePermission : rolePermissions) {
      PermissionResponse permissionResponse =
          permissionMapper.toResponse(rolePermission.getPermission());

      permissionResponses.add(permissionResponse);
    }
    // build the role permission response
    return RolePermissionResponse.builder()
        .roleId(role.getId())
        .roleName(role.getRoleName())
        .permissions(permissionResponses)
        .build();
  }

  @Override
  public RolePermissionResponse updatePermissions(
      Long roleId, AssignPermissionRequest assignPermissionRequest) {
    // fetch the  Role by id
    Role role =
        roleRepository
            .findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found wit id : " + roleId));
    // Remove all existing permissions assigned to the role.
    rolePermissionRepository.deleteByRole(role);
    // assign the updated set of permissions
    return assignPermissions(roleId, assignPermissionRequest);
  }

  @Override
  @Transactional
  public void removePermission(Long roleId, Long permissionId) {
    // fetch the  Role by id
    Role role =
        roleRepository
            .findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found wit id : " + roleId));
    // fetch the permissions By id;

    Permission permission =
        permissionRepository
            .findById(permissionId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Permission not found wit id : " + permissionId));
    // fetch the role-permission mapping

    RolePermission rolePermission =
        rolePermissionRepository
            .findByRoleAndPermission(role, permission)
            .orElseThrow(
                () -> new ResourceNotFoundException("Role permission mapping not found wit id."));
    // Remove the role permission Mapping

    rolePermissionRepository.delete(rolePermission);
  }
}
