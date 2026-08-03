package com.eduforge.permission.dto;

import com.eduforge.common.enums.ModuleName;
import com.eduforge.common.enums.PermissionAction;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponse {

  private Long id;

  private String permissionName;

  private ModuleName module;

  private PermissionAction action;

  private String description;

  private Boolean active;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
