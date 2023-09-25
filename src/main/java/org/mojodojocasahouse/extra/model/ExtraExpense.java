package org.mojodojocasahouse.extra.model;

import jakarta.persistence.Entity;
import jakarta.validation.Valid;

import org.mojodojocasahouse.extra.dto.ExpenseAddingRequest;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "EXPENSES")
@Getter
public class ExtraExpense{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Setter
    @JoinColumn(name = "idUsuario", nullable = false)
    private ExtraUser idUsuario;

    @Setter
    @Column(name = "CONCEPT", nullable = false)
    private String concept;

    @Setter
    @Column(name="AMOUNT", nullable = false)
    private Double amount;

    @Setter
    @Column(name="DATE", nullable = false)
    private String date;

    public ExtraExpense(){}

    public ExtraExpense(ExtraUser idUsuario, String concept, Double amount, String date){
        this.idUsuario = idUsuario;
        this.concept = concept;
        this.amount = amount;
        this.date = date;
    }

    public static ExtraExpense from(@Valid ExpenseAddingRequest expenseAddingRequest) {
        return new ExtraExpense(expenseAddingRequest.getUserId(), expenseAddingRequest.getConcept(), expenseAddingRequest.getAmount(), expenseAddingRequest.getDate());
    }
    



}