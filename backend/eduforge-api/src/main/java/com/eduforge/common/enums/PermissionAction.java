package com.eduforge.common.enums;

import com.eduforge.common.exception.ResourceNotFoundException;
import java.util.Arrays;

public enum PermissionAction {
  CREATE,
  VIEW,
  UPDATE,
  DELETE,
  ACTIVATE,
  APPROVE,
  REJECT,
  IMPORT,
  EXPORT;

  public static PermissionAction from(String value) {
    try {
      return PermissionAction.valueOf(value.trim().toUpperCase());
    } catch (IllegalArgumentException | NullPointerException ex) {
      throw new ResourceNotFoundException(
          "Invalid action. Allowed values are: " + Arrays.toString(PermissionAction.values()));
    }
  }
}
