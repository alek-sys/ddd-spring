package com.example.dddtest.services;

import com.example.dddtest.domain.MonthlyBudget;
import com.example.dddtest.domain.MonthlyBudgetId;
import com.example.dddtest.domain.Spend;
import com.example.dddtest.domain.events.MonthlyBudgetEvent;
import com.example.dddtest.domain.events.MonthlyBudgetExceeded;
import com.example.dddtest.domain.events.NewSpendCreated;
import com.example.dddtest.persistence.MonthlyBudgetRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Java6Assertions.assertThat;

@DataJpaTest
public class MonthlyBudgetServiceTest extends ServiceIntegrationTest {

    @Autowired
    private MonthlyBudgetRepository monthlyBudgetRepository;

    private MonthlyBudgetService service;

    @Before
    public void createService() {
        service = new MonthlyBudgetService(monthlyBudgetRepository, messenger);
    }

    @Test
    public void shouldUpdateMonthlyBudget() {
        LocalDateTime now = LocalDateTime.now();

        Spend spend = new Spend("test", BigDecimal.TEN, now);
        NewSpendCreated event = new NewSpendCreated(now, spend);
        messenger.emit(NewSpendCreated.class, event);

        MonthlyBudgetId monthlyBudgetId = MonthlyBudgetId.of(now.getMonth(), (long) now.getYear());
        MonthlyBudget budget = monthlyBudgetRepository.findById(monthlyBudgetId).get();

        assertThat(budget.getTotalSpent()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void shouldEmitAnEvent() {
        AtomicBoolean triggered = new AtomicBoolean();
        LocalDateTime now = LocalDateTime.now();
        MonthlyBudgetId budgetId = MonthlyBudgetId.of(now);
        monthlyBudgetRepository.save(new MonthlyBudget(budgetId, BigDecimal.ZERO));

        messenger.subscribe(
                MonthlyBudgetEvent.class,
                e -> {
                    MonthlyBudgetExceeded event = (MonthlyBudgetExceeded) e;
                    assertThat(event).isEqualTo(new MonthlyBudgetExceeded(BigDecimal.ZERO, BigDecimal.TEN));
                    triggered.set(true);
                });

        service.onEvent(new NewSpendCreated(now, new Spend("", BigDecimal.TEN, now)));

        assertThat(triggered.get()).isTrue();
    }
}