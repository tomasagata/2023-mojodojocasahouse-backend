package org.mojodojocasahouse.extra.controller;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mojodojocasahouse.extra.dto.ApiResponse;
import org.mojodojocasahouse.extra.dto.ExpenseAddingRequest;
import org.mojodojocasahouse.extra.dto.ExpenseDTO;
import org.mojodojocasahouse.extra.dto.ExpenseFilteringRequest;
import org.mojodojocasahouse.extra.model.ExtraExpense;
import org.mojodojocasahouse.extra.model.ExtraUser;
import org.mojodojocasahouse.extra.service.AuthenticationService;
import org.mojodojocasahouse.extra.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExpensesController {

    private final AuthenticationService userService;

    private final ExpenseService expenseService;


    @PostMapping(value = "/addExpense", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addExpense(
            Principal principal,
            @Valid @RequestBody ExpenseAddingRequest expenseAddingRequest
    ){
        ExtraUser user = userService.getUserByPrincipal(principal);
        ApiResponse response = expenseService.addExpense(user, expenseAddingRequest);
        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }
    
    @GetMapping(path = "/getMyExpenses", produces = "application/json")
    public ResponseEntity<List<ExpenseDTO>> getMyExpenses(Principal principal){
        ExtraUser user = userService.getUserByPrincipal(principal);

        log.info("Retrieving expenses of user: " + principal.getName());

        List <ExtraExpense> listOfExpenses = expenseService.getAllExpensesByUserId(user);
        // Convert ExtraExpense entities to ExpenseDTO
        List<ExpenseDTO> expenseDTOs = new ArrayList<>();
        for (ExtraExpense expense : listOfExpenses) {
            ExpenseDTO expenseDTO = new ExpenseDTO(null, null, null, null, null, null, null);
            expenseDTO.setId(expense.getId());
            expenseDTO.setUserId(expense.getUser().getId());
            expenseDTO.setConcept(expense.getConcept());
            expenseDTO.setAmount(expense.getAmount());
            expenseDTO.setDate(expense.getDate());
            expenseDTO.setCategory(expense.getCategory());
            expenseDTO.setIconId(expense.getIconId());
            expenseDTOs.add(expenseDTO);
        }
    
        return ResponseEntity.ok(expenseDTOs);
    }

    @PostMapping(path = "/getMyExpensesByCategory", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<ExpenseDTO>> getMyExpensesByCategory(Principal principal,
                                                                    @Valid @RequestBody ExpenseFilteringRequest expenseFilteringRequest){
        ExtraUser user = userService.getUserByPrincipal(principal);
        String category = expenseFilteringRequest.getCategory();

        List <ExtraExpense> listOfExpenses = expenseService.getAllExpensesByCategoryByUserId(user, category);
        // Convert ExtraExpense entities to ExpenseDTO
        List<ExpenseDTO> expenseDTOs = new ArrayList<>();
        for (ExtraExpense expense : listOfExpenses) {
            ExpenseDTO expenseDTO = new ExpenseDTO(null, null, null, null, null, null,null);
            expenseDTO.setId(expense.getId());
            expenseDTO.setUserId(expense.getUser().getId());
            expenseDTO.setConcept(expense.getConcept());
            expenseDTO.setAmount(expense.getAmount());
            expenseDTO.setDate(expense.getDate());
            expenseDTO.setCategory(expense.getCategory());
            expenseDTO.setIconId(expense.getIconId());
            expenseDTOs.add(expenseDTO);
        }
        return ResponseEntity.ok(expenseDTOs);
    }


    @GetMapping(path = "/getAllCategories", produces = "application/json")
    public ResponseEntity<List<String>> getMyCategories (Principal principal){
        ExtraUser user = userService.getUserByPrincipal(principal);
        return ResponseEntity.ok(expenseService.getAllCategories(user));
    }

}

