package com.pathik.financetracker.repository;

import com.pathik.financetracker.entity.Transaction;
import com.pathik.financetracker.entity.TransactionType;
import com.pathik.financetracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @EntityGraph(attributePaths = "category")
    Optional<Transaction> findByIdAndUserId(UUID transactionId, UUID userId);

    @EntityGraph(attributePaths = "category")
    Page<Transaction> findAllByUserId(UUID userId, Pageable pageable);

//    UUID user(User user);

    @Query("""
        SELECT t
        FROM Transaction t
        WHERE t.user.id = :userId
          AND (:type IS NULL OR t.transactionType = :type)
          AND (:categoryId IS NULL OR t.category.id = :categoryId)
          AND (:fromDate IS NULL OR t.transactionDate >= :fromDate)
          AND (:toDate IS NULL OR t.transactionDate <= :toDate)
        """)
    @EntityGraph(attributePaths = "category")
    Page<Transaction> findTransactions(
            @Param("userId") UUID userId,
            @Param("type")TransactionType type,
            @Param("categoryId") UUID categoryId,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate  toDate,
            Pageable pageable
            );

    UUID user(User user);
}
