package com.eduforge.auth.entity;

import com.eduforge.common.entity.BaseEntity;
import com.eduforge.role.entity.Role;
import com.eduforge.school.entity.School;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {
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

  private LocalDateTime lastLoginAt;

  private Integer failedLoginAttempts = 0;

  private Boolean accountLocked = false;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public String getUsername() {
    return email;
  }
}
