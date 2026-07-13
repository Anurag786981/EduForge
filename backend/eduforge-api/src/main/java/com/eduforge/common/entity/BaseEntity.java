package com.eduforge.common.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity {

  // Unique identifier for the permission.
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Indicates whether the permission is active.
  @Column(nullable = false)
  private Boolean active;

  // Timestamp when the entity was created.
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  // Timestamp when the entity was last updated.
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  // Sets the initial audit fields before persisting the entity.
  @PrePersist
  public void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  // Updates the audit timestamp before modifying the entity.
  @PreUpdate
  public void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
