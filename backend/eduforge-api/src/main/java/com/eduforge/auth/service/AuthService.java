package com.eduforge.auth.service;

import com.eduforge.auth.dto.*;
import com.eduforge.common.dto.ApiResponse;

public interface AuthService {

  LoginResponse login(LoginRequest loginRequest);

  ApiResponse<Void> changePassword(ChangePasswordRequest request);
}
