package com.example.dddtest.domain;

import com.example.dddtest.domain.events.MonthlyBudgetEvent;
import com.example.dddtest.domain.events.MonthlyBudgetExceeded;
import com.example.dddtest.domain.events.MonthlyBudgetTotalIncreased;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class MonthlyBudgetTest {

    @Test
    public void shouldCreateAnEventWhenLimitExceeded() {
        LocalDateTime now = LocalDateTime.now();
        BigDecimal limit = BigDecimal.TEN;
        BigDecimal amount = BigDecimal.TEN;

        MonthlyBudget budget = new MonthlyBudget(MonthlyBudgetId.of(now), limit);
        Spend spend = new Spend("Test", amount, now);

        budget.addSpend(spend);
        MonthlyBudgetEvent event = budget.addSpend(spend);

        assertThat(event).isEqualTo(new MonthlyBudgetExceeded(limit, BigDecimal.valueOf(20)));
        assertThat(budget.getTotalSpent()).isEqualTo(amount);
    }

    @Test
    public void shouldCreateAnEventWhenLimitIsNotExceeded() {
        LocalDateTime now = LocalDateTime.now();
        BigDecimal limit = BigDecimal.TEN;
        BigDecimal amount = BigDecimal.ONE;

        MonthlyBudget budget = new MonthlyBudget(MonthlyBudgetId.of(now), limit);

        budget.addSpend(new Spend("Test", amount, now));
        MonthlyBudgetEvent event = budget.addSpend(new Spend("Test", amount, now));

        assertThat(budget.getTotalSpent()).isEqualTo(BigDecimal.valueOf(2));
        assertThat(event).isEqualTo(new MonthlyBudgetTotalIncreased(BigDecimal.valueOf(2)));
    }
}