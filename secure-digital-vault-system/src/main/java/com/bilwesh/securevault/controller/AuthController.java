package com.bilwesh.securevault.controller;

import com.bilwesh.securevault.dto.RegisterRequest;
import com.bilwesh.securevault.entity.User;
import com.bilwesh.securevault.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User resgisterUser(@RequestBody RegisterRequest request) {
        return userService.registerUser(request);
    }
}
