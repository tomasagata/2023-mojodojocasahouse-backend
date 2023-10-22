package org.mojodojocasahouse.extra.controller;
import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mojodojocasahouse.extra.dto.ApiResponse;
import org.mojodojocasahouse.extra.dto.ExpenseAddingRequest;
import org.mojodojocasahouse.extra.dto.ExpenseDTO;
import org.mojodojocasahouse.extra.dto.ExpenseEditingRequest;
import org.mojodojocasahouse.extra.dto.ExpenseFilteringRequest;
import org.mojodojocasahouse.extra.model.ExtraUser;
import org.mojodojocasahouse.extra.service.AuthenticationService;
import org.mojodojocasahouse.extra.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExpensesController {

    private final AuthenticationService userService;

    private final ExpenseService expenseService;


    @PostMapping(value = "/addExpense", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addExpense(Principal principal,
                                             @Valid @RequestBody ExpenseAddingRequest expenseAddingRequest){
        ExtraUser user = userService.getUserByPrincipal(principal);

        log.debug("Adding expense to user: \"" + user.getEmail() + "\"");

        ApiResponse response = expenseService.addExpense(user, expenseAddingRequest);
        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @PostMapping(value = "/editExpense", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> editExpense(Principal principal,
                                              @Valid @RequestBody ExpenseEditingRequest expenseEditingRequest){
        ExtraUser user = userService.getUserByPrincipal(principal);

        log.debug("Editing expense of user: \"" + user.getEmail() + "\"");

        ApiResponse response = expenseService.editExpense(user, expenseEditingRequest);
        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/expenses/{id}")
    public ApiResponse deleteExpense(Principal principal, @PathVariable Long id) {
        ExtraUser user = userService.getUserByPrincipal(principal);
    //Check that the user making the deletion is the owner of the expense
        if (!expenseService.isOwner(user, id)) {
            return new ApiResponse("Error. You are not the owner of the expense you are trying to delete");
        }
    // Check if the expense with the given ID exists
        if (!expenseService.existsById(id)) {
            return new ApiResponse("Error. Expense to delete not found");
        }
    // Delete the expense by ID
        expenseService.deleteById(id);
        return new ApiResponse("Expense deleted successfully");
    }

    
    @GetMapping(path = "/getMyExpenses", produces = "application/json")
    public ResponseEntity<List<ExpenseDTO>> getMyExpenses(Principal principal){
        ExtraUser user = userService.getUserByPrincipal(principal);

        log.debug("Retrieving all expenses of user: \"" + principal.getName() + "\"");

        List <ExpenseDTO> listOfExpenses = expenseService.getAllExpensesByUserId(user);
    
        return ResponseEntity.ok(listOfExpenses);
    }
    // Get all expenses of a user by category and min_date and max_Date
    @GetMapping(path = "/getSumOfExpensesOf/{category}/from/{min_date}/to/{max_date}" + //
            "", produces = "application/json")
    public ResponseEntity<BigDecimal> getExpensesByCategoryAndDate(Principal principal, @PathVariable String category, @PathVariable String min_date, @PathVariable String max_date){
        ExtraUser user = userService.getUserByPrincipal(principal);
        Date maxx_date= Date.valueOf(max_date);
        Date minn_date= Date.valueOf(min_date);
        log.debug("Retrieving all expenses of user: \"" + principal.getName() + "\"");

        BigDecimal sumOfExpenses = expenseService.getSumOfExpensesByCategoryAndDate(user, category, minn_date, maxx_date);
    
        return ResponseEntity.ok(sumOfExpenses);
    }

    @PostMapping(path = "/getMyExpensesByCategory", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<ExpenseDTO>> getMyExpensesByCategory(Principal principal,
                                                                    @Valid @RequestBody ExpenseFilteringRequest expenseFilteringRequest){
        ExtraUser user = userService.getUserByPrincipal(principal);
        String category = expenseFilteringRequest.getCategory();

        log.debug("Retrieving expenses of user: \"" + principal.getName() + "\" by category: \"" + category + "\"");

        List<ExpenseDTO> listOfExpenses = expenseService.getAllExpensesByCategoryByUserId(user, category);

        return ResponseEntity.ok(listOfExpenses);
    }


    @GetMapping(path = "/getAllCategories", produces = "application/json")
    public ResponseEntity<List<String>> getMyCategories (Principal principal){
        ExtraUser user = userService.getUserByPrincipal(principal);

        log.debug("Retrieving all expenses of user: \"" + principal.getName() + "\"");

        return ResponseEntity.ok(expenseService.getAllCategories(user));
    }

    @GetMapping(path = "/getMyExpensesFrom", produces = "application/json")
    public ResponseEntity<List<BigDecimal>> getExpensesByDateAndCategory(Principal principal,
        @RequestParam(required = true) List<String> categories,
        @RequestParam(required = false) String minDate, @RequestParam(required = false) String maxDate) {
        ExtraUser user = userService.getUserByPrincipal(principal);
    
        Date minnDate = minDate != null ? Date.valueOf(minDate) : null;
        Date maxxDate = maxDate != null ? Date.valueOf(maxDate) : null;
    
        log.debug("Retrieving expenses for user: \"" + principal.getName() + "\"");
    
        List<BigDecimal> categoryAmounts = new ArrayList<>();
    
        for (String category : categories) {
            BigDecimal totalAmount;
            if (minnDate != null && maxxDate == null) {
                totalAmount = expenseService.getSumOfExpensesByCategoryAfterGivenDate(user, category, minnDate);
            } else if (minnDate != null && maxxDate != null) {
                totalAmount = expenseService.getSumOfExpensesByCategoryAndDate(user, category, minnDate, maxxDate);
            } else if (minnDate == null && maxxDate != null) {
                totalAmount = expenseService.getSumOfExpensesByCategoryBeforeGivenDate(user, category,maxxDate);
            }
            else {
                totalAmount = expenseService.getSumOfExpensesByCategory(user, category);
            }
            if (totalAmount == null) {
                totalAmount = BigDecimal.ZERO;
            }
            categoryAmounts.add(totalAmount);
        }

        return ResponseEntity.ok(categoryAmounts);
    }
    
    
    


}

