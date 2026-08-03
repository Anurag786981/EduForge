package com.eduforge.rolepermission.dto;

import com.eduforge.permission.dto.PermissionResponse;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionResponse {

  private Long roleId;
  private String roleName;
  private String description;
  private List<PermissionResponse> permissions;
}
