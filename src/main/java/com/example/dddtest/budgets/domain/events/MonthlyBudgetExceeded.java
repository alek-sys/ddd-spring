package com.example.dddtest.budgets.domain.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyBudgetExceeded implements MonthlyBudgetEvent {
    private BigDecimal limit;
    private BigDecimal spend;
}
