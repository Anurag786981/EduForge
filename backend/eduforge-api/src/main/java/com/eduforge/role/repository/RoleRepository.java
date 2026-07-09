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
  boolean existsByRoleName(String roleName);
}
