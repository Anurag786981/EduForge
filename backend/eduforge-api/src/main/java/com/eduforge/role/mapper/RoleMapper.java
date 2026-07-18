package com.eduforge.role.mapper;

import com.eduforge.role.dto.RoleRequest;
import com.eduforge.role.dto.RoleResponse;
import com.eduforge.role.entity.Role;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

  // Convert Request DTo to Entity
  public Role toEntity(RoleRequest roleRequest) {

    return Role.builder()
        .roleName(roleRequest.getRoleName())
        .description(roleRequest.getDescription())
        .build();
  }

  // convert Entity to Response DTO
  public RoleResponse toResponse(Role role) {
    return RoleResponse.builder()
        .id(role.getId())
        .schoolId(role.getSchool().getId())
        .schoolName(role.getSchool().getSchoolName())
        .roleName(role.getRoleName())
        .description(role.getDescription())
        .active(role.getActive())
        .createdAt(role.getCreatedAt())
        .updatedAt(role.getUpdatedAt())
        .build();
  }

  // Convert Entity List to Response List
  public List<RoleResponse> toResponseList(List<Role> roles) {
    return roles.stream().map(this::toResponse).collect(Collectors.toList());
  }

  // update existing Entity
  public void updateEntity(RoleRequest request, Role role) {
    role.setRoleName(request.getRoleName());
    role.setDescription(request.getDescription());
  }
}
