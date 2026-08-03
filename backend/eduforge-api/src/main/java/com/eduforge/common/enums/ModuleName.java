package com.eduforge.common.enums;

import com.eduforge.common.exception.ResourceNotFoundException;
import java.util.Arrays;

public enum ModuleName {
  SCHOOL,
  ROLE,
  PERMISSION,
  USER,
  TEACHER,
  STUDENT,
  ATTENDANCE,
  EXAM,
  FEE,
  TIMETABLE,
  QUESTION_PAPER;

  public static ModuleName from(String value) {
    try {
      return ModuleName.valueOf(value.trim().toUpperCase());
    } catch (IllegalArgumentException | NullPointerException ex) {
      throw new ResourceNotFoundException(
          "Invalid module. Allowed values are: " + Arrays.toString(ModuleName.values()));
    }
  }
}
