package com.eduforge.school.entity;

import com.eduforge.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@Table(name = "schools")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class School extends BaseEntity {

  @Column(nullable = false)
  private String schoolName;

  // Government issues DISE Code
  @Column(nullable = false, unique = true)
  private String diseCode;

  @Column(nullable = false)
  private String email;

  private String phoneNumber;

  private String address;
}
