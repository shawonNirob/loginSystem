package com.system.loginSystem.service.imp;

import com.system.loginSystem.dto.request.UpdateProfileRequest;
import com.system.loginSystem.dto.response.UserResponse;
import com.system.loginSystem.entity.User;
import com.system.loginSystem.exception.CustomException;
import com.system.loginSystem.repository.UserRepository;
import com.system.loginSystem.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SecurityContext securityContext;

    @Override
    public UserResponse getCurrentUser(){
        User user = getCurrentUserEntity();
        return mapToUserResponse(user);
    }

    private User getCurrentUserEntity() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new CustomException("User Not Found"));
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhoneNumber(user.getPhoneNumber());

        return response;
    }

    @Override
    @Transactional
    public UserResponse updateProfile(UpdateProfileRequest request) {
        User user = getCurrentUserEntity();

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getProfileImage() != null) {
            try {
                user.setProfileImage(request.getProfileImage().getBytes());
            } catch (IOException e) {
                throw new CustomException("Failed to process Profile Image");
            }
        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return mapToUserResponse(user);
    }
}
