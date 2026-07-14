package com.eduforge.role.repository;

import com.eduforge.role.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  // Retrieves a role using its name.
  Optional<Role> findByRoleName(String roleName);

  // checks whether a role already exists with given name
  /**
   * @param roleName Role Name
   * @return Role Information
   */
  boolean existsByRoleName(String roleName);

  // // Checks whether another role (excluding the current role) already has the given role name.
  /**
   * @param roleName Role Name
   * @param id Role ID to exclude from duplicate validation.
   * @return @return true if another role exists; otherwise false
   */
  boolean existsByRoleNameAndIdNot(String roleName, Long id);
}
