package com.eduforge.auth.exception;

public class InvalidCredentialsException extends RuntimeException {

  /**
   * Throw when the user provides and invalid email and password during the authentication process.
   */
  public InvalidCredentialsException(String message) {
    super(message);
  }
}
