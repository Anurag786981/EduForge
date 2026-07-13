package com.eduforge.role.entity;

import com.eduforge.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Role extends BaseEntity {

  // Name of the role.
  @Column(nullable = false, unique = true)
  private String roleName;

  // Description of the role.
  @Column(length = 500)
  private String description;
}
