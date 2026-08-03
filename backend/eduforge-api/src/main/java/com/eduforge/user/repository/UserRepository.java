package com.eduforge.user.repository;

import com.eduforge.role.entity.Role;
import com.eduforge.school.entity.School;
import com.eduforge.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/** Finds a user by email address. */
public interface UserRepository extends JpaRepository<User, Long> {

  @EntityGraph(attributePaths = {"role", "school"})
  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  List<User> findByRole(Role role);

  List<User> findBySchool(School school);

  Optional<User> findByIdAndRole(Long id, Role role);

  List<User> findBySchoolAndRole(School school, Role role);

  List<User> findByRoleRoleName(String roleName);

  boolean existsBySchoolIdAndRoleRoleNameAndActiveTrue(Long schoolId, String roleName);

  boolean existsBySchoolIdAndRoleRoleNameAndActiveTrueAndIdNot(
      Long schoolId, String roleName, Long userId);
}
