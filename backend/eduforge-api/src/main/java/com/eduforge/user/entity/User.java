package com.eduforge.user.entity;

import com.eduforge.common.entity.BaseEntity;
import com.eduforge.role.entity.Role;
import com.eduforge.school.entity.School;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
  private String firstName;
  private String lastName;

  @Column(unique = true, nullable = false)
  private String email;

  private String password;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id")
  private Role role;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "school_id")
  private School school;

  private Boolean passwordChangeRequired = true;

  @Column(name = "last_login_at")
  private LocalDateTime lastLoginAt;

  @Column(nullable = false)
  private Integer failedLoginAttempts = 0;

  @Column(nullable = false)
  private Boolean accountLocked = false;
}
