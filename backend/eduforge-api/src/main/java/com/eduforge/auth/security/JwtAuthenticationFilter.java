package com.eduforge.auth.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  // Service used to perform Jwt operation
  private final JwtService jwtService;

  // Service used to load the user details from the database
  private final CustomUserDetailsService customUserDetailsService;

  // JwtAuthentication entrypoint for exception handling at JWT
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");

    // If Authorization header is missing or doesn't start with Bearer
    // skip JWT validation

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      // Continue the request to the next filter
      filterChain.doFilter(request, response);
      return;
    }

    // Removes "Bearer " from the Authorization header to extract the JWT.
    final String jwt = authHeader.substring(7);

    try {

      // Extracts the user's email from the JWT.
      final String username = jwtService.extractUsername(jwt);

      // Continues only if a username was successfully extracted.
      if (username != null) {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
          // Loads the user details from the database
          UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

          // Validates the JWt against the Loaded User
          if (jwtService.isTokenValid(jwt, userDetails)) {
            // Creates an authenticated user for Spring Security

            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            // Adds request-specific details (IP address session id)
            authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

            // store the authenticated user in security context

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
          }
        }
      }
    } catch (SignatureException ex) {
      jwtAuthenticationEntryPoint.commence(
          request, response, new BadCredentialsException("Invalid Jwt Token.", ex));
      return;
    } catch (ExpiredJwtException exception) {
      jwtAuthenticationEntryPoint.commence(
          request, response, new BadCredentialsException("JWT token has expired.", exception));
      return;
    } catch (MalformedJwtException ex) {
      jwtAuthenticationEntryPoint.commence(
          request, response, new BadCredentialsException("Malformed JWT token.", ex));
      return;
    } catch (UnsupportedJwtException ex) {
      jwtAuthenticationEntryPoint.commence(
          request, response, new BadCredentialsException("Unsupported JWT token."));
      return;
    } catch (IllegalArgumentException ex) {
      jwtAuthenticationEntryPoint.commence(
          request, response, new BadCredentialsException("JWT token is missing or empty. ", ex));
      return;
    }
    // Continues the request.
    filterChain.doFilter(request, response);
  }
}
