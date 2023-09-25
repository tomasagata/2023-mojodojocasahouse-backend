package org.mojodojocasahouse.extra.controller;
import java.util.UUID;

import org.mojodojocasahouse.extra.dto.ExpenseAddingRequest;
import org.mojodojocasahouse.extra.model.ExtraUser;
import org.mojodojocasahouse.extra.service.AuthenticationService;
import org.mojodojocasahouse.extra.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class ExpensesController {
    @Autowired
    private AuthenticationService userService;
    @Autowired
    private ExpenseService expenseService;



    @PostMapping(value = "/addExpense" , consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addExpense(@CookieValue("JSESSIONID") UUID cookie, @Valid @RequestBody ExpenseAddingRequest expenseAddingRequest){
        userService.validateAuthentication(cookie);
        ExtraUser idUser = userService.getUserBySessionToken(cookie);
        expenseService.addExpense(idUser, expenseAddingRequest);
        return new ResponseEntity<>(
                "Expense added succesfully!",
                HttpStatus.OK
        );
    }
}

