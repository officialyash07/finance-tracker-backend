package com.pathik.financetracker.controller;

import com.pathik.financetracker.dto.auth.LoginResponse;
import com.pathik.financetracker.dto.auth.UserLoginRequest;
import com.pathik.financetracker.dto.auth.UserRegisterRequest;
import com.pathik.financetracker.dto.auth.UserResponse;
import com.pathik.financetracker.service.LoginResult;
import com.pathik.financetracker.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final String ACCESS_TOKEN_COOKIE = "access_token";

    @Value("${app.auth.cookie-secure}")
    private boolean cookieSecure;

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody UserRegisterRequest request) {

        return userService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody UserLoginRequest request, HttpServletResponse response) {

        LoginResult result = userService.login(request);

        Cookie cookie = new Cookie(ACCESS_TOKEN_COOKIE, result.accessToken());

        cookie.setHttpOnly(true);
        cookie.setSecure(cookieSecure);
        cookie.setPath("/");
        cookie.setMaxAge((int) result.expiresIn());

        response.addCookie(cookie);

        return new LoginResponse(result.expiresIn());
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(ACCESS_TOKEN_COOKIE, "");

        cookie.setHttpOnly(true);
        cookie.setSecure(cookieSecure);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }
}