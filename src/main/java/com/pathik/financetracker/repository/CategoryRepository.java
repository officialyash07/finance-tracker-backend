package com.pathik.financetracker.repository;

import com.pathik.financetracker.entity.Category;
import com.pathik.financetracker.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findByCode(String code);

    List<Category> findByActiveTrue();

    List<Category> findByTypeAndActiveTrue(CategoryType type);

    boolean existsByCode(String code);
}
