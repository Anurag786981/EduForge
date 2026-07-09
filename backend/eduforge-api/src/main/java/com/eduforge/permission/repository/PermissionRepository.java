package com.eduforge.permission.repository;

import com.eduforge.permission.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {

    // Retrieves a permission using its name.
    Optional<Permission> findByPermissionName(String permissionName);

    // Checks whether a permission already exists with the given name.
    boolean existsByPermissionName(String permissionName);
}
