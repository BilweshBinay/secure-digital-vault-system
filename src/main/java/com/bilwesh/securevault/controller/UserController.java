package com.bilwesh.securevault.controller;

import com.bilwesh.securevault.dto.UserProfileResponse;
import com.bilwesh.securevault.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("profile")
    public UserProfileResponse profile() {
        return userService.getUserProfile();
    }
}
