package com.pathik.financetracker.service;

public record LoginResult(
        String accessToken,
        long expiresIn
) {
}
