package com.system.loginSystem.service.imp;

import com.system.loginSystem.dto.request.LoginRequest;
import com.system.loginSystem.dto.request.RegistrationRequest;
import com.system.loginSystem.dto.response.AuthResponse;
import com.system.loginSystem.entity.User;
import com.system.loginSystem.exception.CustomException;
import com.system.loginSystem.repository.UserRepository;
import com.system.loginSystem.security.JwtTokenProvider;
import com.system.loginSystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUserName(request.getUsername())
                .orElseThrow(() -> new CustomException("User Not Found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException("Invalid Password");
        }

        String token = jwtTokenProvider.createToken(user.getUsername());
        return new AuthResponse(token, user.getUsername());
    }

    @Override
    public AuthResponse register(RegistrationRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            throw new CustomException("Username Already Exist");
        }

        if(userRepository.existsbyEmail(request.getEmail())){
            throw new CustomException("Email Already Exist");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEnabled(true);

        userRepository.save(user);

        String token = jwtTokenProvider.createToken(user.getUsername());
        return new AuthResponse(token, user.getUsername());
    }
}
