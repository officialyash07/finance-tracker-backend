package com.pathik.financetracker.controller;

import com.pathik.financetracker.dto.transaction.*;
import com.pathik.financetracker.entity.TransactionType;
import com.pathik.financetracker.security.SecurityUtils;
import com.pathik.financetracker.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

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
        UUID userId= SecurityUtils.getCurrentUserId();

        TransactionFilterRequest filter = new TransactionFilterRequest(type, categoryId, fromDate, toDate);

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
