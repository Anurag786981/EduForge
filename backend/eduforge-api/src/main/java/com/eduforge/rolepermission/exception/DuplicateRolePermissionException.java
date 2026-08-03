package com.eduforge.rolepermission.exception;

import com.eduforge.common.exception.BadRequestException;

public class DuplicateRolePermissionException extends BadRequestException {
  public DuplicateRolePermissionException(String message) {
    super(message);
  }
}
