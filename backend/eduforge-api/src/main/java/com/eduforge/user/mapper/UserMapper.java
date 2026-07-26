package com.eduforge.user.mapper;

import com.eduforge.user.dto.UserResponse;
import com.eduforge.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserResponse mapToResponse(User user) {
    return UserResponse.builder()
        .userId(user.getId())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .role(user.getRole().getRoleName())
        .roleId(user.getRole().getId())
        .schoolId(user.getSchool() != null ? user.getSchool().getId() : null)
        .school(user.getSchool() != null ? user.getSchool().getSchoolName() : null)
        .active(user.getActive())
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .accountLocked(user.getAccountLocked())
        .lastLoginAt(user.getLastLoginAt())
        .build();
  }

  // Convert Entity List to Response List
  public List<UserResponse> toResponseList(List<User> users) {
    return users.stream().map(this::mapToResponse).collect(Collectors.toList());
  }
}
