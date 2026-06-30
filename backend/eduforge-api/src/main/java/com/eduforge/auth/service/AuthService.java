package com.eduforge.auth.service;

import com.eduforge.auth.dto.LoginRequest;
import com.eduforge.auth.dto.LoginResponse;
import com.eduforge.auth.dto.RegisterRequest;
import com.eduforge.auth.dto.RegisterResponse;

public interface AuthService {
  RegisterResponse register(RegisterRequest registerRequest);

  LoginResponse login(LoginRequest loginRequest);
}
