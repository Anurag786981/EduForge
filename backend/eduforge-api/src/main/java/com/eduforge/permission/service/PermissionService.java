package com.eduforge.permission.service;

import com.eduforge.permission.dto.PermissionRequest;
import com.eduforge.permission.dto.PermissionResponse;
import java.util.List;

public interface PermissionService {

  PermissionResponse createPermission(PermissionRequest request);

  PermissionResponse getPermissionById(Long permissionId);

  List<PermissionResponse> getAllPermissions();

  PermissionResponse updatePermissionDescription(Long permissionId, PermissionRequest request);

  PermissionResponse activatePermission(Long permissionId);

  PermissionResponse deactivatePermission(Long permissionId);
}
