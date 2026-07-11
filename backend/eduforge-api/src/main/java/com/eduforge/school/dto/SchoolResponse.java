package com.eduforge.school.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SchoolResponse {

  private Long id;
  private String schoolName;
  private String diseCode;
  private String email;
  private String phoneNumber;
  private String address;
  private Boolean active;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
