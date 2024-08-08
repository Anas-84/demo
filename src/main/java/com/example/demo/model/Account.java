package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class Account {
    private static final String MESSAGE = """
            
            Статус счета: %s
            Номер счета: %s
            БИК: %s
            Остаток на счете: %s %s
            """;

    private String number;
    private String currency;
    private BigDecimal sum;
    private boolean active;
    private String bic;

    @Override
    public String toString() {
        return String.format(MESSAGE,
                active ? "ОТКРЫТ" : "ЗАКРЫТ",
                number,
                bic,
                sum, currency);
    }
}
