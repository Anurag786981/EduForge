package com.eduforge.auth.security.impl;

import com.eduforge.auth.security.CustomUserDetailsService;
import com.eduforge.auth.security.principal.CustomUserPrincipal;
import com.eduforge.role.entity.Role;
import com.eduforge.rolepermission.entity.RolePermission;
import com.eduforge.rolepermission.repository.RolePermissionRepository;
import com.eduforge.user.entity.User;
import com.eduforge.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
  // Repository used to fetch user details
  private final UserRepository userRepository;

  // Repository used for fetch the Role and permission from the repo
  private final RolePermissionRepository rolePermissionRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found : " + username));

    // Get the role assigned to the authenticated user
    Role role = user.getRole();

    // retrieve all permissions mapped to the users role
    List<RolePermission> rolePermissions = rolePermissionRepository.findByRole(role);

    // Collection to store role and Permission authorities
    List<GrantedAuthority> authorities = new ArrayList<>();

    // Add the role as a string Security authority
    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));

    // Add each permission as a Spring Security authority
    for (RolePermission rolePermission : rolePermissions) {
      authorities.add(
          new SimpleGrantedAuthority(rolePermission.getPermission().getPermissionName()));
    }

    // return the authenticated user along with role and permissions
    return new CustomUserPrincipal(user, authorities);
  }
}
