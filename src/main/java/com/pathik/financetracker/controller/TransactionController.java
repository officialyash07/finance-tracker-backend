package com.pathik.financetracker.controller;

import com.pathik.financetracker.dto.transaction.TransactionCreateRequest;
import com.pathik.financetracker.dto.transaction.TransactionResponse;
import com.pathik.financetracker.dto.transaction.TransactionUpdateRequest;
import com.pathik.financetracker.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/trnsactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(
            @RequestParam UUID userId,
            @Valid @RequestBody TransactionCreateRequest request
            ){
        return transactionService.createTransaction(userId,request);
    }

    @GetMapping
    public List<TransactionResponse> getAllTransactions(@RequestParam UUID userId){
        return transactionService.getAllTransactions(userId);
    }

    @GetMapping("/{transactionId}")
    public TransactionResponse getTransactionById(@RequestParam UUID userId, @PathVariable UUID transactionId){
        return transactionService.getTransactionById(userId, transactionId);
    }

    @PutMapping("/{transactionId}")
    public TransactionResponse updateTransaction(
            @RequestParam UUID userId,
            @PathVariable UUID transactionId,
            @Valid @RequestBody TransactionUpdateRequest request
            ){
        return transactionService.updateTransaction(userId, transactionId, request);
    }

    @DeleteMapping("/{transactionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction(@RequestParam UUID userId, @PathVariable UUID transactionId){
        transactionService.deleteTransaction(userId, transactionId);
    }
}
