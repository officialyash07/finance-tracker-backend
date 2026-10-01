package com.pathik.financetracker.config;

import com.pathik.financetracker.entity.Category;
import com.pathik.financetracker.entity.CategoryType;
import com.pathik.financetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args){
        if(categoryRepository.count()>0){
            return;
        }

        List<Category> categories=List.of(
                createCategory("SALARY", "Salary", CategoryType.INCOME),
                createCategory("FREELANCE", "Freelance", CategoryType.INCOME),
                createCategory("OTHER_INCOME", "Other Income", CategoryType.INCOME),

                createCategory("FOOD", "Food", CategoryType.EXPENSE),
                createCategory("TRANSPORT", "Transport", CategoryType.EXPENSE),
                createCategory("HOUSING", "Housing", CategoryType.EXPENSE),
                createCategory("SHOPPING", "Shopping", CategoryType.EXPENSE),
                createCategory("ENTERTAINMENT", "Entertainment", CategoryType.EXPENSE),
                createCategory("HEALTHCARE", "Healthcare", CategoryType.EXPENSE),
                createCategory("OTHER_EXPENSE", "Other Expense", CategoryType.EXPENSE)
        );

        categoryRepository.saveAll(categories);
    }

    private Category createCategory(String code, String name, CategoryType type){
        Category category=new Category();
        category.setCode(code);
        category.setName(name);
        category.setType(type);
        category.setActive(true);

        return category;
    };
}
