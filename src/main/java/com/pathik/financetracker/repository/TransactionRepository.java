package com.pathik.financetracker.repository;

import com.pathik.financetracker.entity.Transaction;
import com.pathik.financetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Optional<Transaction> findByIdAndUserId(UUID transactionId, UUID userId);

    List<Transaction> findAllByUserId(UUID userId);

    UUID user(User user);

}
