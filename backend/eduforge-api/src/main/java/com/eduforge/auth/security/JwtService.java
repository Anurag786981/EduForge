package com.eduforge.auth.security;

import com.eduforge.user.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

  // Generate JWt for Authenticated User
  String generateToken(User user);

  // Extracts Email form the Jwt
  String extractUsername(String token);

  // Validates the JWT against the logged-in User
  boolean isTokenValid(String token, UserDetails userDetails);

  // Extracts UserId form the Jwt
  Long extractUserId(String token);

  // Extracts Role form the Jwt
  String extractRole(String token);

  // Extracts schoolId form the Jwt

  Long extractSchoolId(String token);
}
