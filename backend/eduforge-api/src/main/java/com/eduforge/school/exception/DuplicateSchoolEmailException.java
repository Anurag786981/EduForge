package com.eduforge.school.exception;

public class DuplicateSchoolEmailException extends RuntimeException {

  public DuplicateSchoolEmailException(String message) {
    super(message);
  }
}
