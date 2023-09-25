package org.mojodojocasahouse.extra.controller;
import java.util.UUID;

import org.mojodojocasahouse.extra.dto.ExpenseAddingRequest;
import org.mojodojocasahouse.extra.service.AuthenticationService;
import org.mojodojocasahouse.extra.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class ExpensesController {
    
    private AuthenticationService userService;
    private ExpenseService expenseService;


    @PostMapping(path = "/addExpense",consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addExpense(@CookieValue("JSESSIONID") UUID cookie, @Valid @RequestBody ExpenseAddingRequest expenseAddingRequest){
        userService.validateAuthentication(cookie);
        expenseService.addExpense(expenseAddingRequest);
        return new ResponseEntity<>(
                "Expense added succesfully!",
                HttpStatus.OK
        );
    }
}

