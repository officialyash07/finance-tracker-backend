package com.pathik.financetracker.service;

import com.pathik.financetracker.dto.transaction.TransactionCreateRequest;
import com.pathik.financetracker.dto.transaction.TransactionResponse;
import com.pathik.financetracker.dto.transaction.TransactionUpdateRequest;
import com.pathik.financetracker.entity.Category;
import com.pathik.financetracker.entity.CategoryType;
import com.pathik.financetracker.entity.Transaction;
import com.pathik.financetracker.entity.User;
import com.pathik.financetracker.mapper.TransactionMapper;
import com.pathik.financetracker.repository.CategoryRepository;
import com.pathik.financetracker.repository.TransactionRepository;
import com.pathik.financetracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(
            TransactionRepository transactionRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository,
            TransactionMapper transactionMapper
    ) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.transactionMapper = transactionMapper;
    }

    public TransactionResponse createTransaction(UUID userId, TransactionCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.categoryId()));

        if (!category.isActive()) {
            throw new RuntimeException("Category is inactive");
        }

        if (category.getType() != CategoryType.valueOf(request.type().name())) {
            throw new RuntimeException("Transaction type does not match category type");
        }

        Transaction transaction = new Transaction();

        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setTransactionType(request.type());
        transaction.setAmount(request.amount());
        transaction.setDescription(request.description());
        transaction.setTransactionDate(request.transactionDate());

        Transaction savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toResponse(savedTransaction);
    }

    private Transaction getUserTransaction(UUID userId, UUID transactionId) {
        return transactionRepository.findByIdAndUserId(transactionId, userId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + transactionId));
    }

    public TransactionResponse getTransactionById(UUID userId, UUID transactionId) {
        Transaction transaction = getUserTransaction(userId, transactionId);

        return transactionMapper.toResponse(transaction);
    }

    public List<TransactionResponse> getAllTransactions(UUID userId) {
        return transactionRepository.findAllByUserId(userId)
                .stream()
                .map(transactionMapper::toResponse)
                .toList();
    }

    public TransactionResponse updateTransaction(
            UUID userId,
            UUID transactionId,
            TransactionUpdateRequest request
    ) {
        Transaction transaction = getUserTransaction(userId, transactionId);

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.categoryId()));

        if (!category.isActive()) {
            throw new RuntimeException("Category is inactive");
        }

        if (category.getType() != CategoryType.valueOf(request.type().name())) {
            throw new RuntimeException("Transaction type does not match category type");
        }

        transaction.setCategory(category);
        transaction.setTransactionType(request.type());
        transaction.setAmount(request.amount());
        transaction.setDescription(request.description());
        transaction.setTransactionDate(request.transactionDate());

        Transaction updatedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toResponse(updatedTransaction);
    }

    public void deleteTransaction(UUID userId, UUID transactionId) {
        Transaction transaction = getUserTransaction(userId, transactionId);

        transactionRepository.delete(transaction);
    }
}