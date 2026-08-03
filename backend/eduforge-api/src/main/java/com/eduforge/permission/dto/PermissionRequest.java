package com.eduforge.permission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating a permission")
public class PermissionRequest {

  @NotBlank(message = "Module is required")
  @Size(max = 50, message = "Module must not exceed 50 characters")
  private String module;

  @NotBlank(message = "Action is required")
  @Size(max = 30, message = "Action must not exceed 30 characters")
  private String action;

  @Size(max = 255, message = "Description must not exceed 255 characters")
  private String description;
}
