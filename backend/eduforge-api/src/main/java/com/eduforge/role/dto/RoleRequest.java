package com.eduforge.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating a role")
public class RoleRequest {

  @Schema(
      description = "Unique role name",
      example = "TEACHER",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "Role name is Required")
  @Size(min = 3, max = 50, message = "Role name must be between 3 and 50 characters")
  @Pattern(
      regexp = "^[A-Za-z]+( [A-Za-z]+)*$",
      message = "Role name can contain only alphabets and single spaces")
  private String roleName;

  @Schema(description = "Role description", example = "Teacher role with attendance permission")
  @Size(max = 255, message = "Description cannot exceed 255 characters")
  private String description;

  @Schema(description = "School ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "School ID is Required")
  private Long schoolId;
}
