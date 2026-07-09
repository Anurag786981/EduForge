package com.eduforge.role.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    // Unique identifier for the role.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of the role.
    @Column(nullable = false, unique = true)
    private String roleName;

    // Description of the role.
    @Column(length = 500)
    private String description;

    // Indicates whether the role is active.
    @Column(nullable = false)
    private Boolean active;

    // Timestamp when the role was created.
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Timestamp when the role was last updated.
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
