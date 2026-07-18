package com.eduforge.superadmin.mapper;

import com.eduforge.auth.entity.User;
import com.eduforge.superadmin.dto.SuperAdminResponse;
import org.springframework.stereotype.Component;

@Component
public class SuperAdminMapper {

  public SuperAdminResponse toResponse(User user) {
    return SuperAdminResponse.builder()
        .id(user.getId())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .role(user.getRole().getRoleName())
        .active(user.getActive())
        .accountLocked(user.getAccountLocked())
        .lastLoginAt(user.getLastLoginAt())
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .build();
  }
}
