package com.pathik.financetracker.dto.transaction;

import com.pathik.financetracker.entity.TransactionType;

import java.time.LocalDate;
import java.util.UUID;

public record TransactionFilterRequest(
        TransactionType type,
        UUID categoryId,
        LocalDate fromDate,
        LocalDate toDate
) {
}
