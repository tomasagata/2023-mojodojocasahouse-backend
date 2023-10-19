package org.mojodojocasahouse.extra.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.mojodojocasahouse.extra.dto.ApiResponse;
import org.mojodojocasahouse.extra.dto.ExpenseAddingRequest;
import org.mojodojocasahouse.extra.dto.ExpenseEditingRequest;
import org.mojodojocasahouse.extra.dto.ExpenseDTO;
import org.mojodojocasahouse.extra.model.ExtraExpense;
import org.mojodojocasahouse.extra.model.ExtraUser;
import org.mojodojocasahouse.extra.repository.ExtraExpenseRepository;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExtraExpenseRepository expenseRepository;


    public ApiResponse addExpense(ExtraUser user, ExpenseAddingRequest expenseAddingRequest) {
        //create expense entity from request data
        ExtraExpense newExpense = ExtraExpense.from(expenseAddingRequest, user);

        //Save new expense
        expenseRepository.save(newExpense);
        return new ApiResponse("Expense added succesfully!");
    }

    public List<ExpenseDTO> getAllExpensesByUserId(ExtraUser user) {
        List<ExtraExpense> expenseObjects = expenseRepository.findAllExpensesByUser(user);
        return expenseObjects.stream().map(ExtraExpense::asDto).collect(Collectors.toList());
    }

    public List<ExpenseDTO> getAllExpensesByCategoryByUserId(ExtraUser user, String category) {
        List<ExtraExpense> expenseObjects = expenseRepository.findAllExpensesByUserAndCategory(user, category);
        return expenseObjects.stream().map(ExtraExpense::asDto).collect(Collectors.toList());
    }

    public List<String> getAllCategories(ExtraUser user) {
        return expenseRepository.findAllDistinctCategoriesByUser(user);
    }

    public ApiResponse editExpense(ExtraUser user, @Valid ExpenseEditingRequest expenseEditingRequest) {
        // Check if the expense with the given ID exists
        Long expenseId = expenseEditingRequest.getId();
        Optional<ExtraExpense> expenseOptional = expenseRepository.findById(expenseId);
    
        if (expenseOptional.isPresent()) {
            // Get the existing expense
            ExtraExpense existingExpense = expenseOptional.get();
    
            // Update the properties of the existing expense with the new data
            existingExpense.updateFrom(expenseEditingRequest, user);
    
            // Save the updated expense
            expenseRepository.save(existingExpense);
    
            return new ApiResponse("Expense edited successfully!");
        } else {
            // Return an error response if the expense with the given ID is not found
            return new ApiResponse("Expense not found");
        }
    }

    public boolean existsById(Long id) {
        return expenseRepository.existsById(id);
    }

    public void deleteById(Long id) {
        expenseRepository.deleteById(id);
    }

    public boolean isOwner(ExtraUser user, Long id) {
        return expenseRepository.existsByIdAndUser(id, user);
    }

    public BigDecimal getSumOfExpensesByCategoryAndDate(ExtraUser user, String category, Date min_date, Date max_date) {
        return expenseRepository.getSumOfExpensesOfAnUserByCategoryAndDateInterval(user, category, min_date, max_date);
    }
    
}
