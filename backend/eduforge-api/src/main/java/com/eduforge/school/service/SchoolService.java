package com.eduforge.school.service;

import com.eduforge.school.dto.SchoolRequest;
import com.eduforge.school.dto.SchoolResponse;
import java.util.List;

public interface SchoolService {

  // Create New School
  SchoolResponse createSchool(SchoolRequest schoolRequest);

  // Update an existing school
  /**
   * @param schoolId Unique School id
   * @param schoolRequest Details of School what we are updating
   * @return School Information
   */
  SchoolResponse updateSchool(Long schoolId, SchoolRequest schoolRequest);

  // retrieves School by its ID
  /**
   * @param schoolId Unique School id
   * @return School Information
   */
  SchoolResponse getSchoolById(Long schoolId);

  // Retrieves All School
  /**
   * @return School Information
   */
  List<SchoolResponse> getAllSchools();

  // Activates a school
  /**
   * @param schoolId Information
   * @return Updated School
   */
  SchoolResponse activateSchool(Long schoolId);

  // Deactivate a school
  /**
   * @param schoolId Information
   * @return Updated School
   */
  SchoolResponse deactivateSchool(Long schoolId);
}
