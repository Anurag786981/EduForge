package com.eduforge.rolepermission.entity;

import com.eduforge.permission.entity.Permission;
import com.eduforge.role.entity.Role;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role_permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Associated role for the mapping
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  // Associated permission for the mapping
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "permission_id", nullable = false)
  private Permission permission;

  // Check whether mapping is active or not
  @Column(nullable = false)
  private Boolean active;

  // Timestamp when mapping was created
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  // TimeStamp When the mapping was last updated
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  // Sets the initial audit fields before persisting the role.
  @PrePersist
  public void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  // Updates the audit timestamp before modifying the role.
  @PreUpdate
  public void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
