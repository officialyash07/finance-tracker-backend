package com.pathik.financetracker.dto.dashboard;

import java.math.BigDecimal;
import java.util.UUID;

public record CategorySummaryResponse(
        UUID categoryId,
        String categoryName,
        BigDecimal totalAmount
) {
}
