package com.eduforge.common.exception;

import com.eduforge.auth.exception.InvalidCredentialsException;
import com.eduforge.auth.exception.UserAlreadyExistsException;
import com.eduforge.common.dto.ErrorResponse;
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

    com.eduforge.common.dto.ErrorResponse errorResponse =
        com.eduforge.common.dto.ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.CONTINUE.value())
            .error("Conflict")
            .message(ex.getMessage())
            .path(request.getRequestURI())
            .build();
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(
      InvalidCredentialsException ex, HttpServletRequest request) {

    com.eduforge.common.dto.ErrorResponse errorResponse =
        com.eduforge.common.dto.ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.UNAUTHORIZED.value())
            .error("Unauthorized")
            .message(ex.getMessage())
            .path(request.getRequestURI())
            .build();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
  }
}
