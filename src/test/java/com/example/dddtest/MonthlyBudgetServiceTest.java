package com.example.dddtest;

import com.example.dddtest.domain.MonthlyBudget;
import com.example.dddtest.domain.MonthlyBudgetId;
import com.example.dddtest.domain.Spend;
import com.example.dddtest.domain.events.MonthlyBudgetEvent;
import com.example.dddtest.domain.events.MonthlyBudgetExceeded;
import com.example.dddtest.domain.events.MonthlyBudgetTotalIncreased;
import com.example.dddtest.domain.events.NewSpendCreated;
import com.example.dddtest.messaging.LocalMessenger;
import com.example.dddtest.persistence.MonthlyBudgetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MonthlyBudgetServiceTest {

    @Autowired
    private LocalMessenger<NewSpendCreated> messenger;

    @Autowired
    private MonthlyBudgetRepository monthlyBudgetRepository;

    @Test
    public void shouldUpdateMonthlyBudget() {
        LocalDateTime now = LocalDateTime.now();

        Spend spend = new Spend("test", BigDecimal.TEN, now);
        NewSpendCreated event = new NewSpendCreated(now, spend);
        messenger.emit(event);

        MonthlyBudgetId monthlyBudgetId = MonthlyBudgetId.of(now.getMonth(), (long) now.getYear());
        MonthlyBudget budget = monthlyBudgetRepository.findById(monthlyBudgetId).get();

        assertThat(budget.getTotalSpent()).isEqualTo(BigDecimal.TEN.setScale(2));
    }
}