package com.eduforge.role.dto;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {

  private Long id;
  private String roleName;
  private String description;
  private Boolean active;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Long schoolId;
  private String schoolName;
}
