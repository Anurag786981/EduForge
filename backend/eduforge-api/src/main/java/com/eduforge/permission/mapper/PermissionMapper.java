package com.eduforge.permission.mapper;

import com.eduforge.permission.dto.PermissionRequest;
import com.eduforge.permission.dto.PermissionResponse;
import com.eduforge.permission.entity.Permission;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {

  // Convert Request DTo to Entity
  public Permission toEntity(PermissionRequest permissionRequest) {

    return Permission.builder().description(permissionRequest.getDescription()).build();
  }

  public PermissionResponse toResponse(Permission permission) {
    return PermissionResponse.builder()
        .id(permission.getId())
        .permissionName(permission.getPermissionName())
        .description(permission.getDescription())
        .active(permission.getActive())
        .module(permission.getModule())
        .action(permission.getAction())
        .createdAt(permission.getCreatedAt())
        .updatedAt(permission.getUpdatedAt())
        .build();
  }
}
