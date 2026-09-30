package com.pathik.financetracker.controller;

import com.pathik.financetracker.dto.transaction.TransactionCreateRequest;
import com.pathik.financetracker.dto.transaction.TransactionResponse;
import com.pathik.financetracker.dto.transaction.TransactionUpdateRequest;
import com.pathik.financetracker.security.SecurityUtils;
import com.pathik.financetracker.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<TransactionResponse> getAllTransactions(){
        UUID userId= SecurityUtils.getCurrentUserId();

        return transactionService.getAllTransactions(userId);
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
