package com.eduforge.superadmin.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SuperAdminResponse {
  private Long id;

  private String firstName;

  private String lastName;

  private String email;

  private String role;

  private boolean active;
  private boolean accountLocked;
  private LocalDateTime lastLoginAt;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
