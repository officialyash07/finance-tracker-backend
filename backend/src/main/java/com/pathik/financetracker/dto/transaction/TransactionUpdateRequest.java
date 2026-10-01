package com.pathik.financetracker.dto.transaction;

import com.pathik.financetracker.entity.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionUpdateRequest(

        @NotNull
        UUID categoryId,

        @NotNull
        TransactionType type,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount,

        @Size(max = 500)
        String description,

        @NotNull
        LocalDate transactionDate
) {
}
