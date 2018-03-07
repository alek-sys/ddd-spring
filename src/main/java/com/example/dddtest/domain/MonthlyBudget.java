package com.example.dddtest.domain;

import com.example.dddtest.domain.events.MonthlyBudgetEvent;
import com.example.dddtest.domain.events.MonthlyBudgetExceeded;
import com.example.dddtest.domain.events.MonthlyBudgetTotalIncreased;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class MonthlyBudget {

    public static BigDecimal DEFAULT_LIMIT = BigDecimal.TEN;

    private MonthlyBudget() {}

    public MonthlyBudget(MonthlyBudgetId id, BigDecimal monthlyLimit) {
        this.id = id;
        this.monthlyLimit = monthlyLimit;
    }

    @EmbeddedId
    private MonthlyBudgetId id;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal monthlyLimit = DEFAULT_LIMIT;

    public BigDecimal getTotalSpent() {
        return total;
    }

    public MonthlyBudgetEvent addSpend(Spend spend) {
        final BigDecimal newTotal = this.total.add(spend.amount);
        if (newTotal.compareTo(this.monthlyLimit) > 0) {
            return new MonthlyBudgetExceeded(this.monthlyLimit, newTotal);
        }

        this.total = newTotal;

        return new MonthlyBudgetTotalIncreased(this.total);
    }
}
