package com.bilwesh.securevault.service;

import com.bilwesh.securevault.dto.RegisterRequest;
import com.bilwesh.securevault.entity.Role;
import com.bilwesh.securevault.entity.User;
import com.bilwesh.securevault.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(RegisterRequest request) {
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.USER);

        return userRepository.save(user);
    }
}
