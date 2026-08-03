package com.eduforge.user.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

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
