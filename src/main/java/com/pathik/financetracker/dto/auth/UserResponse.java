package com.pathik.financetracker.dto.auth;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String firstName,
        String lastName,
        String preferredCurrency,
        Instant createdAt
) {
}
