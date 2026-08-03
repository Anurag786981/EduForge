package com.eduforge.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccountLockRequest {

  @NotNull(message = "Account lock status required.")
  private Boolean accountLocked;
}
