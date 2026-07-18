package com.eduforge.auth.repository;

import com.eduforge.auth.entity.User;
import com.eduforge.role.entity.Role;
import com.eduforge.school.entity.School;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/** Finds a user by email address. */
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  List<User> findByRole(Role role);

  List<User> findBySchool(School school);

  Optional<User> findByIdAndRole(Long id, Role role);

  List<User> findBySchoolAndRole(School school, Role role);

  List<User> findByRoleRoleName(String roleName);
}
