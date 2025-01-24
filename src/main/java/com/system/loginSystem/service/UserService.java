package com.system.loginSystem.service;

import com.system.loginSystem.dto.request.UpdateProfileRequest;
import com.system.loginSystem.dto.response.UserResponse;

public interface UserService {
    UserResponse getCurrentUser();
    UserResponse updateProfile(UpdateProfileRequest request);
}
