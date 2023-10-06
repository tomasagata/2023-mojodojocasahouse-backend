package org.mojodojocasahouse.extra.dto;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseDTO {
    
    public ExpenseDTO(Long id, Long userId, String concept, BigDecimal amount, Date date, String category, Short iconId) {
        this.id = id;
        this.userId = userId;
        this.concept = concept;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.iconId=iconId;
    }
    private Long id;
    private Long userId;
    private String concept;
    private BigDecimal amount;
    private Date date;
    private String category;
    private Short iconId;

}
