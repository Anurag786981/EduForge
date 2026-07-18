package com.eduforge.superadmin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSuperAdminRequest {

  @NotBlank(message = "First Name is required.")
  @Size(max = 30, message = "first name cannot exceed 30 characters.")
  private String firstName;

  @NotBlank(message = "Last Name is required.")
  @Size(max = 30, message = "first name cannot exceed 30 characters.")
  private String lastName;

  @NotBlank(message = "Email is required.")
  @Email(message = "Invalid email format.")
  private String email;

  @NotBlank(message = "Password is required.")
  @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
      message =
          "Password must be 8–20 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character.")
  private String password;
}
