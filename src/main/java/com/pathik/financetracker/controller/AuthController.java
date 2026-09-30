package com.pathik.financetracker.controller;

import com.pathik.financetracker.dto.auth.LoginResponse;
import com.pathik.financetracker.dto.auth.UserLoginRequest;
import com.pathik.financetracker.dto.auth.UserRegisterRequest;
import com.pathik.financetracker.dto.auth.UserResponse;
import com.pathik.financetracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody UserRegisterRequest request){
        return userService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody UserLoginRequest request){
        return userService.login(request);
    }
}
