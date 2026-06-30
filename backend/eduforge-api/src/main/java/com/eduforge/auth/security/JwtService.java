package com.eduforge.auth.security;

import com.eduforge.auth.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

  // Generate JWt for Authenticated User
  String generateToken(User user);

  // Extracts Email form the Jwt
  String extractUsername(String token);

  // Validates the JWT against the logged-in User
  boolean isTokenValid(String token, UserDetails userDetails);
}
