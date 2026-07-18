package com.eduforge.permission.service.impl;

import com.eduforge.common.enums.ModuleName;
import com.eduforge.common.enums.PermissionAction;
import com.eduforge.common.exception.ResourceNotFoundException;
import com.eduforge.permission.dto.PermissionRequest;
import com.eduforge.permission.dto.PermissionResponse;
import com.eduforge.permission.entity.Permission;
import com.eduforge.permission.exception.DuplicatePermissionException;
import com.eduforge.permission.mapper.PermissionMapper;
import com.eduforge.permission.repository.PermissionRepository;
import com.eduforge.permission.service.PermissionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

  private final PermissionRepository permissionRepository;
  private final PermissionMapper permissionMapper;

  @Override
  public PermissionResponse createPermission(PermissionRequest request) {

    // Convert Strings to Enums
    ModuleName moduleName = ModuleName.from(request.getModule());
    PermissionAction action = PermissionAction.from(request.getAction());

    // creating PermissionName
    String permissionName = moduleName.name() + "_" + action.name();

    // validate Duplicate Role
    if (permissionRepository.existsByPermissionName(permissionName)) {
      throw new DuplicatePermissionException(" Permission exists with name " + permissionName);
    }

    Permission permission = permissionMapper.toEntity(request);

    permission.setModule(moduleName);
    permission.setAction(action);
    permission.setPermissionName(permissionName);

    Permission savedPermission = permissionRepository.save(permission);

    return permissionMapper.toResponse(savedPermission);
  }

  @Override
  public PermissionResponse getPermissionById(Long permissionId) {
    Permission permission =
        permissionRepository
            .findById(permissionId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Permission not found with id : " + permissionId));

    return permissionMapper.toResponse(permission);
  }

  @Override
  public List<PermissionResponse> getAllPermissions() {
    return permissionRepository.findAll().stream().map(permissionMapper::toResponse).toList();
  }

  @Override
  public PermissionResponse updatePermissionDescription(
      Long permissionId, PermissionRequest request) {
    // Only description is editable.
    // Module, Action and Permission Name are immutable.
    /*
    * Today:
         ROLE_CREATE   assigned to:Super Admin School Admin
      If someone changes it to: ROLE_DELETE all existing role mappings become incorrect.*/
    Permission permission =
        permissionRepository
            .findById(permissionId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Permission not found with id : " + permissionId));

    permission.setDescription(request.getDescription());

    Permission updatedPermission = permissionRepository.save(permission);
    return permissionMapper.toResponse(updatedPermission);
  }

  @Override
  public PermissionResponse activatePermission(Long permissionId) {
    return updatePermissionStatus(permissionId, true);
  }

  @Override
  public PermissionResponse deactivatePermission(Long permissionId) {
    return updatePermissionStatus(permissionId, false);
  }

  private PermissionResponse updatePermissionStatus(Long permissionId, boolean status) {
    // Check Permission Exists
    Permission permission =
        permissionRepository
            .findById(permissionId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Permission not found with id : " + permissionId));

    permission.setActive(status);
    Permission updatedPermission = permissionRepository.save(permission);
    return permissionMapper.toResponse(updatedPermission);
  }
}
