package com.eduforge.school.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SchoolRequest {

  @NotBlank(message = "School Name is Required")
  @Size(max = 100, message = "School name cannot exceeds 100 characters")
  private String schoolName;

  @NotBlank(message = "DISE code is required")
  @Size(min = 11, max = 11, message = "DISE code must be exactly 11 digits")
  @Pattern(regexp = "^[0-9]+$", message = "DISE code must contain only digits")
  @Schema(description = "Government issued DISE code", example = "78698180161")
  private String diseCode;

  @NotBlank(message = "Email is required")
  @Email(message = "Please enter a VALID address")
  @Schema(description = "School email address", example = "abcschool@gmail.com")
  private String email;

  @NotBlank(message = "Phone code is required")
  @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
  @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Please enter valid 10 digit mobile number")
  private String phoneNumber;

  @NotBlank(message = "Address is required")
  @Size(max = 255, message = "Address Cannot exceed 255 characters")
  private String address;
}
