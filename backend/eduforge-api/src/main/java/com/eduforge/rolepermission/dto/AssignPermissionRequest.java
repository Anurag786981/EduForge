package com.eduforge.rolepermission.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignPermissionRequest {

  @NotEmpty(message = "permission Ids cannot be empty")
  private List<Long> permissionsIds;
}
