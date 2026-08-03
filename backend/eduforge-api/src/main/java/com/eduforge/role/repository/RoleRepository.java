package com.eduforge.role.repository;

import com.eduforge.role.entity.Role;
import com.eduforge.school.entity.School;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  // Retrieves a role by school and role name
  Optional<Role> findBySchoolAndRoleName(School school, String roleName);

  // checks whether a role already exists in a school
  /**
   * @param roleName Role Name
   * @param school Name
   * @return Role Information
   */
  boolean existsBySchoolAndRoleName(School school, String roleName);

  // // Checks whether another role (excluding the current role) already has the given role name.
  /**
   * @param roleName Role Name
   * @param id Role ID to exclude from duplicate validation.
   * @param school for school details
   * @return @return true if another role exists; otherwise false
   */
  boolean existsBySchoolAndRoleNameAndIdNot(School school, String roleName, Long id);

  // get aLL ROLES OF SCHOOL
  List<Role> findBySchool(School school);

  // get Role on the bases of Role Name like SUPER_ADMIN
  Optional<Role> findByRoleName(String roleName);
}
