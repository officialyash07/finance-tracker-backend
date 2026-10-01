package com.pathik.financetracker.controller;

import com.pathik.financetracker.dto.auth.ChangePasswordRequest;
import com.pathik.financetracker.dto.auth.UserResponse;
import com.pathik.financetracker.dto.auth.UserUpdateRequest;
import com.pathik.financetracker.security.SecurityUtils;
import com.pathik.financetracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse getCurrentUser(){
        UUID userId= SecurityUtils.getCurrentUserId();

        return userService.getCurrentUser(userId);
    }

    @PutMapping("/me")
    public UserResponse updateCurrentUser(@Valid @RequestBody UserUpdateRequest request){
        UUID userId=SecurityUtils.getCurrentUserId();

        return userService.updateCurrentUser(userId,request);
    }

    @PutMapping("/me/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@Valid @RequestBody ChangePasswordRequest request){
        UUID userId=SecurityUtils.getCurrentUserId();

        userService.changePassword(userId,request);
    }
}
