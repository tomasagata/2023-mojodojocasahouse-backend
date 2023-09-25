package org.mojodojocasahouse.extra.service;

import org.mojodojocasahouse.extra.dto.ExpenseAddingRequest;
import org.mojodojocasahouse.extra.model.ExtraExpense;
import org.mojodojocasahouse.extra.model.ExtraUser;
import org.mojodojocasahouse.extra.repository.ExtraExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class ExpenseService {
    private final ExtraExpenseRepository expenseRepository;
    public ExpenseService(ExtraExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public ResponseEntity<Object> addExpense(ExtraUser user, @Valid ExpenseAddingRequest expenseAddingRequest) {
        //create expense entity from request data
        ExtraExpense newExpense = ExtraExpense.from(expenseAddingRequest, user);

        //Save new expense
        ExtraExpense savedExpense = expenseRepository.save(newExpense);
        return new ResponseEntity<>("Expense added succesfully!", HttpStatus.OK);
    }
}
