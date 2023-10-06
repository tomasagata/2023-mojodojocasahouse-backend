package org.mojodojocasahouse.extra.dto;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ExpenseDTO {
    
    public ExpenseDTO(Long id, Long userId, String concept, BigDecimal amount, Date date) {
        this.id = id;
        this.userId = userId;
        this.concept = concept;
        this.amount = amount;
        this.date = date.toString();
    }
    private Long id;
    private Long userId;
    private String concept;
    private BigDecimal amount;
    private String date;

}
