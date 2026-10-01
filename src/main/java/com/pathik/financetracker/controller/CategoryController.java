package com.pathik.financetracker.controller;

import com.pathik.financetracker.dto.category.CategoryResponse;
import com.pathik.financetracker.entity.CategoryType;
import com.pathik.financetracker.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponse> getCategories(@RequestParam(name = "type", required = false) CategoryType type){
        if (type==null){
            return categoryService.getAllActiveCategories();
        }

        return categoryService.getActiveCategoriesByType(type);
    }
}
