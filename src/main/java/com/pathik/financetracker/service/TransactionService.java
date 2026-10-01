package com.pathik.financetracker.service;

import com.pathik.financetracker.dto.transaction.*;
import com.pathik.financetracker.entity.Category;
import com.pathik.financetracker.entity.CategoryType;
import com.pathik.financetracker.entity.Transaction;
import com.pathik.financetracker.entity.User;
import com.pathik.financetracker.exception.BusinessException;
import com.pathik.financetracker.exception.ResourceNotFoundException;
import com.pathik.financetracker.mapper.TransactionMapper;
import com.pathik.financetracker.repository.CategoryRepository;
import com.pathik.financetracker.repository.TransactionRepository;
import com.pathik.financetracker.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.categoryId()));

        if (!category.isActive()) {
            throw new BusinessException("Category is inactive");
        }

        if (category.getType() != CategoryType.valueOf(request.type().name())) {
            throw new BusinessException("Transaction type does not match category type");
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
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + transactionId));
    }

    public TransactionResponse getTransactionById(UUID userId, UUID transactionId) {
        Transaction transaction = getUserTransaction(userId, transactionId);

        return transactionMapper.toResponse(transaction);
    }

    public TransactionPageResponse getAllTransactions(UUID userId, TransactionFilterRequest filter, Pageable pageable) {

        if (filter.fromDate() != null
                && filter.toDate() != null
                && filter.fromDate().isAfter(filter.toDate())) {

            throw new BusinessException(
                    "fromDate must be before or equal to toDate"
            );
        }

        Page<Transaction> transactionPage=transactionRepository.findTransactions(
                userId,
                filter.type(),
                filter.categoryId(),
                filter.fromDate(),
                filter.toDate(),
                pageable
                );

        List<TransactionResponse> transactions=transactionPage.getContent()
                .stream()
                .map(transactionMapper::toResponse)
                .toList();

        return new TransactionPageResponse(
                transactions,
                transactionPage.getNumber(),
                transactionPage.getSize(),
                transactionPage.getTotalElements(),
                transactionPage.getTotalPages()
        );
    }

    @Transactional
    public TransactionResponse updateTransaction(
            UUID userId,
            UUID transactionId,
            TransactionUpdateRequest request
    ) {
        Transaction transaction = getUserTransaction(userId, transactionId);

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.categoryId()));

        if (!category.isActive()) {
            throw new BusinessException("Category is inactive");
        }

        if (category.getType() != CategoryType.valueOf(request.type().name())) {
            throw new BusinessException("Transaction type does not match category type");
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