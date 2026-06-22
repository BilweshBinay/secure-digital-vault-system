package com.bilwesh.securevault.service;

import com.bilwesh.securevault.dto.RegisterRequest;
import com.bilwesh.securevault.entity.User;

public interface UserService {
    User registerUser(RegisterRequest request);
}
