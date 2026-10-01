package com.pathik.financetracker.dto.transaction;

import com.pathik.financetracker.entity.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        UUID categoryId,
        String categoryName,
        TransactionType type,
        BigDecimal amount,
        String description,
        LocalDate transactionDate,
        Instant createdAt,
        Instant updatedAt
) {
}
