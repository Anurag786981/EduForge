package com.eduforge.common.exception;

import com.eduforge.auth.exception.InvalidCredentialsException;
import com.eduforge.auth.exception.InvalidJwtTokenException;
import com.eduforge.auth.exception.JwtExpiredException;
import com.eduforge.auth.exception.UserAlreadyExistsException;
import com.eduforge.common.dto.ErrorResponse;
import com.eduforge.permission.exception.DuplicatePermissionException;
import com.eduforge.role.exception.DuplicateRoleException;
import com.eduforge.school.exception.DuplicateDiseCodeException;
import com.eduforge.school.exception.DuplicateSchoolEmailException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

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

  @ExceptionHandler(DuplicateRoleException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateRoleException(
      DuplicateRoleException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI()));
  }

  @ExceptionHandler(DuplicatePermissionException.class)
  public ResponseEntity<ErrorResponse> handleDuplicatePermissionException(
      DuplicatePermissionException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI()));
  }

  // JWT and Generic Exception handler

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

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleMethodNotSupportedError(
      HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(
            buildErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage(), request.getRequestURI()));
  }

  // Handle URL Not correct
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI()));
  }

  // Handle JSON In correct error
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Request body is missing or invalid JSON",
                request.getRequestURI()));
  }

  // Handle Invalid Path variable like roles/abc(400)
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI()));
  }

  // Handle Missing Request Parameter
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
      MissingServletRequestParameterException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI()));
  }

  // Handle Missing Authorization Header
  @ExceptionHandler(MissingRequestHeaderException.class)
  public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(
      MissingRequestHeaderException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI()));
  }

  // Handle Media type exception
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(
      HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
        .body(
            buildErrorResponse(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                "Content-Type 'text/plain' is not supported. Please yse application/json.",
                request.getRequestURI()));
  }

  // Handle Internal Server Error
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getRequestURI()));
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
