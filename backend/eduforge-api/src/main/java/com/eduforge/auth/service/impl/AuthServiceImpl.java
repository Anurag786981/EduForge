package com.eduforge.auth.service.impl;

import com.eduforge.auth.dto.*;
import com.eduforge.auth.exception.InvalidCredentialsException;
import com.eduforge.auth.security.JwtService;
import com.eduforge.auth.service.AuthService;
import com.eduforge.common.dto.ApiResponse;
import com.eduforge.common.exception.ResourceNotFoundException;
import com.eduforge.role.repository.RoleRepository;
import com.eduforge.school.repository.SchoolRepository;
import com.eduforge.user.entity.User;
import com.eduforge.user.repository.UserRepository;
import com.sun.jdi.request.InvalidRequestStateException;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final SchoolRepository schoolRepository;
    private final RoleRepository roleRepository;

    // maximum Allowed failed login Attempts before locking the account.
    private static final int MAX_FAILED_LOGIN_ATTEMPTS = 3;
    private String token;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        // Step 1 : Find user by email
        User user =
                userRepository
                        .findByEmail(loginRequest.getEmail())
                        .orElseThrow(
                                () ->
                                        new InvalidCredentialsException(
                                                "No Account found with email : " + loginRequest.getEmail()));

        // Active Account validation
        if (!user.getActive()) {
            throw new InvalidRequestStateException(
                    "Your Account is inactive. Please contact the administrator.");
        }
        // Prevents login if the users account is locked.
        if (user.getAccountLocked()) {
            throw new InvalidRequestStateException(
                    "Your Account is locked. Please contact the administrator.");
        }

        // Step 2 : Validate Password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            handleFailedLogin(user);
        }
        // Resets failed login attempts after the successful authentication .
        resetFailedLogin(user);

        // Update the Last Successful login timestamp
        user.setLastLoginAt(LocalDateTime.now());

        // Save all changes in single db operation
        userRepository.save(user);

        // Step 3: Generate JWT
        String token = jwtService.generateToken(user);

        // Step 4: Build LoginResponse
        return LoginResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().getRoleName())
                .schoolId(user.getSchool() != null ? user.getSchool().getId() : null)
                .schoolName(user.getSchool() != null ? user.getSchool().getSchoolName() : null)
                .token(token)
                .active(true)
                .mustChangePassword(user.getPasswordChangeRequired())
                .build();
    }

    @Override
    public ApiResponse<Void> changePassword(ChangePasswordRequest request) {

        // Retrieves the currently authenticated user using the JWt stored in the security Context.
        // Returns the logged-in user's entity for the secured business operations.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // fetch the authenticated user's details from the database using email from the JWT.
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("User Not Found with email: " + email));
        // Verifies that entered current Password matches the stored encrypted password.

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new InvalidRequestStateException("Current password is incorrect.");
        }
        // Validate that the new password matches the confirm password.
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new InvalidRequestStateException("New password and confirm password do not match.");
        }
        // Ensures the new password is different from the  current password.
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new InvalidRequestStateException(
                    "New password must be different from the current password.");
        }
        // Encrypts and updates the user's password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        // Marks that the user has successfully changed the password.
        user.setPasswordChangeRequired(false);

        // persists the updated user details
        userRepository.save(user);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Password changed Successfully. Please login Again")
                .build();
    }

    /**
     * Increment failed login attempts and lock the account after reaching the maximum allowed limit.
     */
    private void handleFailedLogin(User user) {

        // Increment the failed login attempts counter
        user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

        // Calculate Remaining attempts
        int remainingAttempts = MAX_FAILED_LOGIN_ATTEMPTS - user.getFailedLoginAttempts();

        if (user.getFailedLoginAttempts() >= MAX_FAILED_LOGIN_ATTEMPTS) {

            // Locks the user account after reaching the maximum failed login attempts.
            user.setAccountLocked(true);
            userRepository.save(user);

            throw new InvalidRequestStateException(
                    "Your account has been locked after "
                            + MAX_FAILED_LOGIN_ATTEMPTS
                            + " failed login attempts. please contact the administrator .");
        }
        userRepository.save(user);
        throw new InvalidRequestStateException(
                "Invalid email or password. " + remainingAttempts + " login attempt(s) remaining.");
    }

    // Resets failed login attempts after a successful login
    private void resetFailedLogin(User user) {

        // Resets the failed login attempts after a successful authentication.
        if (user.getFailedLoginAttempts() > 0) {
            user.setFailedLoginAttempts(0);
        }
    }
}
