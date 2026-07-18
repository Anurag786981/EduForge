package com.eduforge.superadmin.service.impl;

import com.eduforge.auth.entity.User;
import com.eduforge.auth.exception.UserAlreadyExistsException;
import com.eduforge.auth.repository.UserRepository;
import com.eduforge.common.exception.ResourceNotFoundException;
import com.eduforge.role.entity.Role;
import com.eduforge.role.repository.RoleRepository;
import com.eduforge.superadmin.dto.CreateSuperAdminRequest;
import com.eduforge.superadmin.dto.SuperAdminResponse;
import com.eduforge.superadmin.dto.UpdateAdminStatusRequest;
import com.eduforge.superadmin.dto.UpdateSuperAdminRequest;
import com.eduforge.superadmin.mapper.SuperAdminMapper;
import com.eduforge.superadmin.service.SuperAdminService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final SuperAdminMapper superAdminMapper;

  @Override
  public SuperAdminResponse createSuperAdmin(CreateSuperAdminRequest request) {

    // Validate email
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new UserAlreadyExistsException(
          "An Account with this email already exists. :" + request.getEmail());
    }

    // fetch SUPER_ADMIN Role
    Role superAdminRole =
        roleRepository
            .findByRoleName("SUPER_ADMIN")
            .orElseThrow(() -> new ResourceNotFoundException("SUPER_ADMIN role not found"));

    // Create User

    User user =
        User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(superAdminRole)
            .school(null)
            .passwordChangeRequired(true)
            .failedLoginAttempts(0)
            .accountLocked(false)
            .build();
    // Save
    User savedUser = userRepository.save(user);
    return superAdminMapper.toResponse(savedUser);
  }

  @Override
  public List<SuperAdminResponse> getAllSuperAdmins() {

    List<User> superAdmins = userRepository.findByRoleRoleName("SUPER_ADMIN");

    return superAdmins.stream().map(superAdminMapper::toResponse).toList();
  }

  @Override
  public SuperAdminResponse getSuperAdminById(Long id) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Super Admin not found with id: " + id));

    if (!"SUPER_ADMIN".equalsIgnoreCase(user.getRole().getRoleName())) {
      throw new ResourceNotFoundException("Super Admin not found with id: " + id);
    }

    return superAdminMapper.toResponse(user);
  }

  @Override
  public SuperAdminResponse updateSuperAdmin(Long id, UpdateSuperAdminRequest request) {
    User user = findSuperAdmin(id);

    if (!user.getEmail().equals(request.getEmail())) {
      Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

      if (existingUser.isPresent()) {
        throw new UserAlreadyExistsException("Email already exists: " + request.getEmail());
      }
    }

    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());

    userRepository.save(user);

    return superAdminMapper.toResponse(user);
  }

  @Override
  public SuperAdminResponse updateStatus(Long id, UpdateAdminStatusRequest request) {

    User user = findSuperAdmin(id);

    user.setActive(request.getActive());

    userRepository.save(user);

    return superAdminMapper.toResponse(user);
  }

  private User findSuperAdmin(Long id) {

    User user =
        userRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Super Admin not found with id: " + id));

    if (!"SUPER_ADMIN".equalsIgnoreCase(user.getRole().getRoleName())) {
      throw new ResourceNotFoundException("Super Admin not found with id: " + id);
    }

    return user;
  }
}
