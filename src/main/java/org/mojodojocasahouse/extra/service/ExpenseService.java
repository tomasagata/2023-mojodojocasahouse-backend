package org.mojodojocasahouse.extra.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.mojodojocasahouse.extra.dto.ApiResponse;
import org.mojodojocasahouse.extra.dto.ExpenseAddingRequest;
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

    //get all expenses of the user that is making the request
    public List<ExpenseDTO> getAllExpensesByUserId(ExtraUser user) {
        List<ExtraExpense> expenseObjects =  expenseRepository.findAllExpensesByUserId(user);
        return expenseObjects.stream().map(ExtraExpense::asDto).collect(Collectors.toList());
    }
}
