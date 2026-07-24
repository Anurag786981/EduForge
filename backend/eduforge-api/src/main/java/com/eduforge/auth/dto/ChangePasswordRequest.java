package com.eduforge.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

  @NotBlank(message = "Current Password is Required")
  private String currentPassword;

  @NotBlank(message = "New Password is Required")
  private String newPassword;

  @NotBlank(message = "Confirm Password id required")
  private String confirmPassword;
}
