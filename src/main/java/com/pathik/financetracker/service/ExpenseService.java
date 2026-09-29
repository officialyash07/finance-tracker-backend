package com.pathik.financetracker.service;

import com.pathik.financetracker.entity.Expense;
import com.pathik.financetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense createExpense(Expense expense){
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id){
        return expenseRepository.findById(id).orElseThrow(()->new RuntimeException("Expense not found with id: "+id));
    }

    public Expense updateExpense(Long id, Expense expense){
        Expense existingExpense=getExpenseById(id);
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setCategory(expense.getCategory());
        existingExpense.setDescription(expense.getDescription());
        existingExpense.setExpenseDate(expense.getExpenseDate());

        return expenseRepository.save(existingExpense);
    }

    public void deleteExpense(Long id){
        Expense existingExpense=getExpenseById(id);
        expenseRepository.delete(existingExpense);
    }
}
