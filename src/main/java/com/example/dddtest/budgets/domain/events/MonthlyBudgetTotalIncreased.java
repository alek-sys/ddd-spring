package com.example.dddtest.budgets.domain.events;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class MonthlyBudgetTotalIncreased implements MonthlyBudgetEvent {
    private final BigDecimal total;
}
