package com.eduforge.auth.exception;

public class JwtExpiredException extends RuntimeException {

  public JwtExpiredException(String message) {
    super(message);
  }
}
