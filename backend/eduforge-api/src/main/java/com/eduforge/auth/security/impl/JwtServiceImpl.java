package com.eduforge.auth.security.impl;

import com.eduforge.auth.constant.JwtClaims;
import com.eduforge.auth.security.JwtService;
import com.eduforge.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
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

    // Adds Custom Claim
    claims.put(JwtClaims.USER_ID, user.getId());
    claims.put(JwtClaims.ROLE, user.getRole().getRoleName());
    if (user.getSchool() != null) {
      claims.put(JwtClaims.SCHOOL_ID, user.getSchool().getId());
    }
    // claims.put("role",user.getRole().user);

    return Jwts.builder()

        // Custom claim Added
        .claims(claims)
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

  /*
  Extract the logged-in user unique id
  Reads the custom claim
   */
  @Override
  public Long extractUserId(String token) {

    // returns the User id  stored as the JWt subject
    return extractClaim(token, claims -> claims.get(JwtClaims.USER_ID, Long.class));
  }

  /*
  Extract the logged-in user Role
  role: SCHOOL_ADMIN
  Reads the custom claim
   */
  @Override
  public String extractRole(String token) {
    return extractClaim(token, claims -> claims.get(JwtClaims.ROLE, String.class));
  }

  /*
  Extract the logged-in user school id from the jwt
  schoolId:1
  For Super Admin this value will be null because a super admin is not associated aith a single school
   */

  @Override
  public Long extractSchoolId(String token) {
    return extractClaim(token, claims -> claims.get(JwtClaims.SCHOOL_ID, Long.class));
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

  /*
   * Extracts any claims for the JWt Token
   *
   * Instead of writing separate Code to read each claim (UserId, role,SchoolId)
   * Example:
   * extractClaim(token, Claims::getSubject)
   * extractClaim(token, Claims-> claims.get(role))
   *
   * */

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    // read all claims Present inside the Payload
    Claims claims = extractAllClaims(token);
    // Apply the requested extraction logic and return the value.
    return claimsResolver.apply(claims);
  }
}
