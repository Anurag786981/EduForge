package com.eduforge.auth.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {

  // User Information
  private Long id;
  private String firstName;
  private String lastname;
  private String email;

  // Authorization
  private String role;

  // Authentication
  private String token;

  // Future Multi-School Support
  private Long schoolId;
  private String schoolName;

  // Account Status
  private boolean active;
}
