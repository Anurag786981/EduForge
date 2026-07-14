package com.eduforge.school.repository;

import com.eduforge.school.entity.School;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {

  // Retrieves a school using its unique dise code
  Optional<School> findByDiseCode(String diseCode);

  // Checks whether a school already exits with the given DISE code
  boolean existsByDiseCode(String diseCode);

  // Checks whether Another school already has given dise code
  /**
   * @param diseCode Dise code
   * @param id current School id
   * @return School Information
   */
  boolean existsByDiseCodeAndIdNot(String diseCode, Long id);

  // Checks if the school email is already registered
  boolean existsByEmail(String email);
}
