package com.eduforge.common.exception;

import com.eduforge.auth.exception.InvalidCredentialsException;
import com.eduforge.auth.exception.InvalidJwtTokenException;
import com.eduforge.auth.exception.JwtExpiredException;
import com.eduforge.auth.exception.UserAlreadyExistsException;
import com.eduforge.common.dto.ErrorResponse;
import com.eduforge.school.exception.DuplicateDiseCodeException;
import com.eduforge.school.exception.DuplicateSchoolEmailException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
    // Here we are building line by line but in other method we called our helper
    com.eduforge.common.dto.ErrorResponse errorResponse =
        com.eduforge.common.dto.ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Validation Failed")
            .message("Input Validation Failed")
            .path(request.getRequestURI())
            .validationErrors(errors)
            .build();
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleUserAlreadyExistException(
      UserAlreadyExistsException ex, HttpServletRequest request) {

    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI()));
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(
      InvalidCredentialsException ex, HttpServletRequest request) {

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request.getRequestURI()));
  }

  @ExceptionHandler(DuplicateDiseCodeException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateDiseCodeException(
      DuplicateDiseCodeException ex, HttpServletRequest request) {

    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI()));
  }

  @ExceptionHandler(DuplicateSchoolEmailException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateSchoolEmailException(
      DuplicateSchoolEmailException ex, HttpServletRequest request) {

    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI()));
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
      ResourceNotFoundException ex, HttpServletRequest request) {

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI()));
  }

  @ExceptionHandler(InvalidJwtTokenException.class)
  public ResponseEntity<ErrorResponse> handleInvalidJwtTokenException(
      InvalidJwtTokenException ex, HttpServletRequest request) {

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request.getRequestURI()));
  }

  @ExceptionHandler(JwtExpiredException.class)
  public ResponseEntity<ErrorResponse> handleJwtExpiredException(
      InvalidJwtTokenException ex, HttpServletRequest request) {

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request.getRequestURI()));
  }

  // Helper method for builder
  private ErrorResponse buildErrorResponse(HttpStatus status, String message, String path) {
    return ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(status.value())
        .error(status.getReasonPhrase())
        .message(message)
        .path(path)
        .build();
  }
}
