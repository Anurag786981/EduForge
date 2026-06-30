package com.eduforge.auth.security.impl;

import com.eduforge.auth.entity.User;
import com.eduforge.auth.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

  // Read the secret key from the Application.prop
  @Value("${jwt.secret}")
  private String secretKey;

  // Read the Token Expiry time from application.prop
  @Value("${jwt.expiration}")
  private long jwtExpiration;

  // Creates a Secret key Object used to sign in and Verify JWTs
  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  @Override
  public String generateToken(User user) {

    // Stores Additional information inside the Jwt Payload
    Map<String, Object> claims = new HashMap<>();

    // Stores the User's role for authorization
    // claims.put("role",user.getRole().user);

    return Jwts.builder()
        // Adds Custom Claim
        // .claims()
        // Sets the logged-in users email as the subject
        .subject(user.getEmail())
        // Sets Token creation time
        .issuedAt(new Date(System.currentTimeMillis()))
        // Sets token Expiration time
        .expiration(new Date(System.currentTimeMillis() + jwtExpiration))

        // signs the token using secret time
        .signWith(getSigningKey())
        // build and return the JWT
        .compact();
  }

  @Override
  public String extractUsername(String token) {

    // returns the email stored as the JWt subject
    return extractAllClaims(token).getSubject();
  }

  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    // Extracts the email from JWT
    String username = extractUsername(token);

    // Return True if the email matches and the token is not expired
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  // Check whether JWT is Expired or Not
  private boolean isTokenExpired(String token) {
    return extractAllClaims(token).getExpiration().before(new Date());
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        // Sends the secret key used to verify the Jwt Signature
        .verifyWith(getSigningKey())
        // builds teh JWT Parser
        .build()
        // parse the Signed JWT
        .parseSignedClaims(token)
        // Return all JWt claims
        .getPayload();
  }
}
