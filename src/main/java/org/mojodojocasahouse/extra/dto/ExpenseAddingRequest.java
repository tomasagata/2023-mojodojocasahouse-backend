package org.mojodojocasahouse.extra.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ExpenseAddingRequest {
    private String concept;
    private String amount;
    private String date;
    
    public ExpenseAddingRequest(String concept, String amount, String date) {
        //Validar datos!!
        this.concept = concept;
        this.amount = amount;
        this.date = date;
        }
    
    public ExpenseAddingRequest() {
    }

}
