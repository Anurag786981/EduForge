package com.eduforge.school.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "schools")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class School {

  // Unique identifier for the school
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String schoolName;

  // Government issues DISE Code
  @Column(nullable = false, unique = true)
  private String diseCode;

  @Column(nullable = false)
  private String email;

  private String phoneNumber;

  private String address;

  // Indicates Whether the school is active or not
  private Boolean active;

  // Timestamp When the school was created
  private LocalDateTime createdAt;

  // Timestamp when the school details were last Updated
  private LocalDateTime updatedAt;

  @PrePersist
  public void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  public void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
