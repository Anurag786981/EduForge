package com.eduforge.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequest {

  @Email(message = "Please enter valid email address")
  @NotBlank(message = "Email is Required")
  private String email;

  @NotBlank(message = "Password is required")
  private String password;
}
