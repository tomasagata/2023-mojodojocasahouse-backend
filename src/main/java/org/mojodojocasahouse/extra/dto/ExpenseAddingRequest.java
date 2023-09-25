package org.mojodojocasahouse.extra.dto;


import org.mojodojocasahouse.extra.model.ExtraUser;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ExpenseAddingRequest {
        private String concept;
        private Double amount;
        private String date;
    
        public ExpenseAddingRequest(String concept, Double amount, String date) {
            //Validar datos!!
            this.concept = concept;
            this.amount = amount;
            this.date = date;
        }
    
        public ExpenseAddingRequest() {
        }
        //necesito obtener el ID del usuario que esta logeado y que esta añadiendo el gasto
        public ExtraUser getUserId() {
            return null;
        }
}
