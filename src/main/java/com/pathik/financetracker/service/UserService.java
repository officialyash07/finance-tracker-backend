package com.pathik.financetracker.service;

import com.pathik.financetracker.dto.auth.UserRegisterRequest;
import com.pathik.financetracker.dto.auth.UserResponse;
import com.pathik.financetracker.entity.User;
import com.pathik.financetracker.mapper.UserMapper;
import com.pathik.financetracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }


    public UserResponse register(UserRegisterRequest request){
        String email=request.email().trim().toLowerCase();

        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already registered.");
        }

        User user=new User();

        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setFirstName(request.firstName().trim());
        user.setLastName(request.lastName().trim());
        user.setPreferredCurrency(request.preferredCurrency());

        return userMapper.toResponse(userRepository.save(user));
    }
}

