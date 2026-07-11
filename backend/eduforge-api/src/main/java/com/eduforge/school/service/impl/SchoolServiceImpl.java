package com.eduforge.school.service.impl;

import com.eduforge.common.exception.ResourceNotFoundException;
import com.eduforge.school.dto.SchoolRequest;
import com.eduforge.school.dto.SchoolResponse;
import com.eduforge.school.entity.School;
import com.eduforge.school.exception.DuplicateDiseCodeException;
import com.eduforge.school.repository.SchoolRepository;
import com.eduforge.school.service.SchoolService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

  private final SchoolRepository schoolRepository;

  @Override
  public SchoolResponse createSchool(SchoolRequest schoolRequest) {

    if (schoolRepository.existsByDiseCode(schoolRequest.getDiseCode())) {
      throw new DuplicateDiseCodeException("School Already exist with this DISE code");
    }

    School school =
        School.builder()
            .schoolName(schoolRequest.getSchoolName())
            .diseCode(schoolRequest.getDiseCode())
            .email(schoolRequest.getEmail())
            .phoneNumber(schoolRequest.getPhoneNumber())
            .address(schoolRequest.getAddress())
            .active(true)
            .build();

    School savedSchool = schoolRepository.save(school);
    return mapToResponse(savedSchool);
  }

  @Override
  public SchoolResponse updateSchool(Long schoolId, SchoolRequest schoolRequest) {

    School school =
        schoolRepository
            .findById(schoolId)
            .orElseThrow(
                () -> new ResourceNotFoundException("School Not Found with Given id " + schoolId));

    if (schoolRepository.existsByDiseCodeAndIdNot(schoolRequest.getDiseCode(), schoolId)) {
      throw new DuplicateDiseCodeException("Another School already exists with this Dise Code");
    }

    school.setSchoolName(schoolRequest.getSchoolName());
    school.setDiseCode(schoolRequest.getDiseCode());
    school.setEmail(schoolRequest.getEmail());
    school.setPhoneNumber(school.getPhoneNumber());
    school.setAddress(schoolRequest.getAddress());

    School upadtedSchool = schoolRepository.save(school);
    return mapToResponse(upadtedSchool);
  }

  @Override
  public SchoolResponse getSchoolById(Long schoolId) {

    School school =
        schoolRepository
            .findById(schoolId)
            .orElseThrow(
                () -> new ResourceNotFoundException("School not found with id : " + schoolId));
    return mapToResponse(school);
  }

  @Override
  public List<SchoolResponse> getAllSchools() {
    List<School> schools = schoolRepository.findAll();
    return schools.stream().map(this::mapToResponse).toList();
  }

  @Override
  public SchoolResponse activateSchool(Long schoolId) {
    return updateSchoolStatus(schoolId, true);
  }

  @Override
  public SchoolResponse deactivateSchool(Long schoolId) {
    return updateSchoolStatus(schoolId, false);
  }

  // Converts School entity to schoolResponse
  private SchoolResponse mapToResponse(School school) {
    return SchoolResponse.builder()
        .id(school.getId())
        .schoolName(school.getSchoolName())
        .diseCode(school.getDiseCode())
        .email(school.getEmail())
        .phoneNumber(school.getPhoneNumber())
        .address(school.getAddress())
        .active(school.getActive())
        .createdAt(school.getCreatedAt())
        .updatedAt(school.getUpdatedAt())
        .build();
  }

  private SchoolResponse updateSchoolStatus(Long schoolId, Boolean status) {
    School school =
        schoolRepository
            .findById(schoolId)
            .orElseThrow(
                () -> new ResourceNotFoundException("School Not found with ID " + schoolId));

    school.setActive(status);

    School upadtedSchool = schoolRepository.save(school);
    return mapToResponse(upadtedSchool);
  }
}
