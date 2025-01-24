package com.system.loginSystem.service;

import com.system.loginSystem.dto.request.LoginRequest;
import com.system.loginSystem.dto.request.RegistrationRequest;
import com.system.loginSystem.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegistrationRequest request);
}
