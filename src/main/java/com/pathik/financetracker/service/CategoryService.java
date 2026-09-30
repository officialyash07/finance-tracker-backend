package com.pathik.financetracker.service;

import com.pathik.financetracker.dto.category.CategoryResponse;
import com.pathik.financetracker.entity.Category;
import com.pathik.financetracker.entity.CategoryType;
import com.pathik.financetracker.mapper.CategoryMapper;
import com.pathik.financetracker.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper=categoryMapper;
    }

    public List<CategoryResponse> getAllActiveCategories(){
        return categoryRepository.findByActiveTrue()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    public List<CategoryResponse> getActiveCategoriesByType(CategoryType type){
        return categoryRepository.findByTypeAndActiveTrue(type)
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }
}
