package com.eduforge.user.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {

  private Long userId;
  private String firstName;
  private String lastName;
  private String email;
  private Long roleId;
  private String role;
  private Long schoolId;
  private String school;
  private Boolean active;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Boolean accountLocked;
  private LocalDateTime lastLoginAt;


}
