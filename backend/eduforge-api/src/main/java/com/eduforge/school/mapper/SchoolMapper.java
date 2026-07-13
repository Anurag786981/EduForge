package com.eduforge.school.mapper;

import com.eduforge.school.dto.SchoolResponse;
import com.eduforge.school.entity.School;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SchoolMapper {

  // Converts School entity to schoolResponse
  public SchoolResponse mapToResponse(School school) {
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

  // Convert Entity List to Response List
  public List<SchoolResponse> toResponseList(List<School> schools) {
    return schools.stream().map(this::mapToResponse).collect(Collectors.toList());
  }
}
