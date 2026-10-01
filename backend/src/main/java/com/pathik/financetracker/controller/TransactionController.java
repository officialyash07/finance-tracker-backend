package com.pathik.financetracker.controller;

import com.pathik.financetracker.dto.transaction.*;
import com.pathik.financetracker.entity.TransactionType;
import com.pathik.financetracker.exception.BusinessException;
import com.pathik.financetracker.security.SecurityUtils;
import com.pathik.financetracker.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "transactionDate",
            "amount",
            "createdAt",
            "updatedAt"
    );

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(@Valid @RequestBody TransactionCreateRequest request){
        UUID userId= SecurityUtils.getCurrentUserId();

        return transactionService.createTransaction(userId,request);
    }

    @GetMapping
    public TransactionPageResponse getAllTransactions(
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            Pageable pageable
    ){

        if (pageable.getPageSize() > 100) {
            throw new BusinessException("Page size cannot exceed 100");
        }

        UUID userId= SecurityUtils.getCurrentUserId();

        TransactionFilterRequest filter = new TransactionFilterRequest(type, categoryId, fromDate, toDate);

        for (Sort.Order order : pageable.getSort()) {
            if (!ALLOWED_SORT_FIELDS.contains(order.getProperty())) {
                throw new BusinessException(
                        "Sorting by '" + order.getProperty() + "' is not allowed"
                );
            }
        }

        return transactionService.getAllTransactions(userId,filter,pageable);
    }

    @GetMapping("/{transactionId}")
    public TransactionResponse getTransactionById(@PathVariable UUID transactionId){
        UUID userId= SecurityUtils.getCurrentUserId();

        return transactionService.getTransactionById(userId, transactionId);
    }

    @PutMapping("/{transactionId}")
    public TransactionResponse updateTransaction(@PathVariable UUID transactionId, @Valid @RequestBody TransactionUpdateRequest request){
        UUID userId= SecurityUtils.getCurrentUserId();

        return transactionService.updateTransaction(userId, transactionId, request);
    }

    @DeleteMapping("/{transactionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction(@PathVariable UUID transactionId){
        UUID userId= SecurityUtils.getCurrentUserId();

        transactionService.deleteTransaction(userId, transactionId);
    }
}
