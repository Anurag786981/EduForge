package com.eduforge.auth.security.impl;

import com.eduforge.auth.entity.User;
import com.eduforge.auth.repository.UserRepository;
import com.eduforge.auth.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
  // Repository used to fetch user details
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found : " + username));
    return user;
  }
}
