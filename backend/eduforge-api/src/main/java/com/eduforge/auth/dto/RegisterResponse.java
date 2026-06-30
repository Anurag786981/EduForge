package com.eduforge.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {

  private Long userId;
  private String firstName;
  private String lastName;
  private String email;
  private String role;
}
