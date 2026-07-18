package com.eduforge.superadmin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSuperAdminRequest {

  @NotBlank(message = "First Name is required.")
  @Size(max = 30, message = "first name cannot exceed 30 characters.")
  private String firstName;

  @NotBlank(message = "Last Name is required.")
  @Size(max = 30, message = "last name cannot exceed 30 characters.")
  private String lastName;

  @NotBlank(message = "Email is required.")
  @Email(message = "Invalid email format.")
  private String email;
}
