package com.eduforge.permission.entity;

import com.eduforge.common.entity.BaseEntity;
import jakarta.persistence.*;
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
  @Column(nullable = false, unique = true)
  private String permissionName;

  // Description of the permission.
  @Column(length = 500)
  private String description;

  // Module to which the permission belongs.
  @Column(nullable = false)
  private String module;
}
