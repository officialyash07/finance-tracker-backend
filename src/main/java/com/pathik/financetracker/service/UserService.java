package com.pathik.financetracker.service;

import com.pathik.financetracker.dto.auth.LoginResponse;
import com.pathik.financetracker.dto.auth.UserLoginRequest;
import com.pathik.financetracker.dto.auth.UserRegisterRequest;
import com.pathik.financetracker.dto.auth.UserResponse;
import com.pathik.financetracker.entity.User;
import com.pathik.financetracker.mapper.UserMapper;
import com.pathik.financetracker.repository.UserRepository;
import com.pathik.financetracker.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtService=jwtService;
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

    public LoginResponse login(UserLoginRequest request){
        String email=request.email().trim().toLowerCase();

        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new IllegalArgumentException("Invalid email or password"));

        if(!passwordEncoder.matches(request.password(), user.getPasswordHash())){
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getId(),user.getEmail());

        return new LoginResponse(token, "Bearer", jwtService.getExpirationMillis()/1000);
    }
}

