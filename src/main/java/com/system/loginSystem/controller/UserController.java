package com.system.loginSystem.controller;

import com.system.loginSystem.dto.request.UpdateProfileRequest;
import com.system.loginSystem.dto.response.UserResponse;
import com.system.loginSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(@ModelAttribute UpdateProfileRequest request){
        return ResponseEntity.ok(userService.updateProfile(request));
    }
}
