package com.eduforge.role.entity;

import com.eduforge.common.entity.BaseEntity;
import com.eduforge.school.entity.School;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
    name = "roles",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"school_id", "role_name"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Role extends BaseEntity {

  // Name of the role.
  @Column(name = "role_name", nullable = false)
  private String roleName;

  // Description of the role.
  @Column(length = 500)
  private String description;

  // School to which Role belongs

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "school_id")
  private School school;
}
