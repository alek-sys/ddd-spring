package com.example.dddtest.budgets.domain.events;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class MonthlyBudgetExceeded implements MonthlyBudgetEvent {
    private final BigDecimal limit;
    private final BigDecimal spend;
}
