package com.eduforge.permission.entity;

import com.eduforge.common.entity.BaseEntity;
import com.eduforge.common.enums.ModuleName;
import com.eduforge.common.enums.PermissionAction;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permissions")
@Entity
public class Permission extends BaseEntity {
  // id came form BaseEntity

  // Name of the permission.
  @Column(name = "permission_name", nullable = false, unique = true, length = 100)
  @NotBlank(message = "Permission name is required")
  private String permissionName;

  // Description of the permission.
  @Size(max = 255, message = "Description must not exceed 255 characters")
  @Column(length = 255)
  private String description;

  // Module to which the permission belongs.
  @NotNull(message = "Module is required")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 50)
  private ModuleName module;

  @NotNull(message = "Action is required")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private PermissionAction action;
}
