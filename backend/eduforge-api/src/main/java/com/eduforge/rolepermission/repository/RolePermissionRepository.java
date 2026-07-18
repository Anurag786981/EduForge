package com.eduforge.rolepermission.repository;

import com.eduforge.permission.entity.Permission;
import com.eduforge.role.entity.Role;
import com.eduforge.rolepermission.entity.RolePermission;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

  // Retrieves all permissions assigned to the specified role.
  List<RolePermission> findByRole(Role role);

  boolean existsByRoleAndPermission(Role role, Permission permission);

  void deleteByRole(Role role);

  Optional<RolePermission> findByRoleAndPermission(Role role, Permission permission);
}
