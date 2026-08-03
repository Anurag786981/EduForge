package com.eduforge.rolepermission.entity;

import com.eduforge.common.entity.BaseEntity;
import com.eduforge.permission.entity.Permission;
import com.eduforge.role.entity.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
    name = "role_permissions",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "permission_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RolePermission extends BaseEntity {

  // Associated role for the mapping
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  // Associated permission for the mapping
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "permission_id", nullable = false)
  private Permission permission;
}
