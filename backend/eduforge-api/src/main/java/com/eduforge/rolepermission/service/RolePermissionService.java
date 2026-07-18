package com.eduforge.rolepermission.service;

import com.eduforge.rolepermission.dto.AssignPermissionRequest;
import com.eduforge.rolepermission.dto.RolePermissionResponse;

public interface RolePermissionService {

  RolePermissionResponse assignPermissions(Long roleId, AssignPermissionRequest request);

  RolePermissionResponse getPermissionsByRole(Long roleId);

  RolePermissionResponse updatePermissions(
      Long roleId, AssignPermissionRequest assignPermissionRequest);

  void removePermission(Long roleId, Long permissionId);
}
