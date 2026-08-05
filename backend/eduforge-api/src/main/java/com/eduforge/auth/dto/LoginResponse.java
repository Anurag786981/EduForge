package com.eduforge.auth.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class LoginResponse {

  // User Information
  private Long id;
  private String firstName;
  private String lastName;
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

  private boolean mustChangePassword;


}
