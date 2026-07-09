package com.eduforge.permission.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permissions")
@Entity
public class Permission {
  // Unique identifier for the permission.
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Name of the permission.
  @Column(nullable = false, unique = true)
  private String permissionName;

  // Description of the permission.
  @Column(length = 500)
  private String description;

  // Module to which the permission belongs.
  @Column(nullable = false)
  private String module;

  // Indicates whether the permission is active.
  @Column(nullable = false)
  private Boolean active;

  // Timestamp when the permission was created.
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  // Timestamp when the permission was last updated.
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  // Sets the initial audit fields before persisting the permission.
  @PrePersist
  public void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  // Updates the audit timestamp before modifying the permission.
  @PreUpdate
  public void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
