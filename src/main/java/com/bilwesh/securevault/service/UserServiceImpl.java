package com.bilwesh.securevault.service;

import com.bilwesh.securevault.dto.LoginRequest;
import com.bilwesh.securevault.dto.RegisterRequest;
import com.bilwesh.securevault.dto.AuthResponse;
import com.bilwesh.securevault.dto.UserProfileResponse;
import com.bilwesh.securevault.entity.Role;
import com.bilwesh.securevault.entity.User;
import com.bilwesh.securevault.repository.UserRepository;
import com.bilwesh.securevault.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public User registerUser(RegisterRequest request) {
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    @Override
    public AuthResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid  Password");
        }

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new AuthResponse(token);
    }

    @Override
    public UserProfileResponse getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        return new UserProfileResponse(
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}
