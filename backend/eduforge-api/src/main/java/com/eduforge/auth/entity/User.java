package com.eduforge.auth.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;
  private String lastName;

  @Column(unique = true, nullable = false)
  private String email;

  private String password;
  private String role;
  private Boolean active;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
