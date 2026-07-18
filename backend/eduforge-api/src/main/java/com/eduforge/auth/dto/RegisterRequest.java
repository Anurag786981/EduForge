package com.eduforge.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

  @NotBlank(message = "First Name is Required")
  @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
  private String firstName;

  @NotBlank(message = "Last Name is Required")
  @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
  private String lastName;

  @Email(message = "Invalid Email")
  @NotBlank(message = "Email is Required")
  @Pattern(
      regexp = "^(?!.*\\.\\.)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
      message = "Email must be a valid format, e.g., user@example.com.")
  private String email;

  @NotBlank(message = "Password is required")
  @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
      message =
          "Password must be 8–20 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character.")
  private String password;

  @NotNull(message = "School Id is required")
  private Long schoolId;

  @NotNull(message = "Role id is required")
  private Long roleId;
}
