package com.example.dddtest.budgets.integration;

import com.example.dddtest.ServiceIntegrationTest;
import com.example.dddtest.budgets.domain.MonthlyBudget;
import com.example.dddtest.budgets.domain.MonthlyBudgetId;
import com.example.dddtest.budgets.domain.events.MonthlyBudgetExceeded;
import com.example.dddtest.spends.domain.Spend;
import com.example.dddtest.spends.domain.events.NewSpendCreated;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

@DataJpaTest
@SpringCloudTest
public class MonthlyBudgetServiceTest extends ServiceIntegrationTest {

    @Autowired
    private MonthlyBudgetRepository monthlyBudgetRepository;

    @Autowired
    private Processor processor;

    @Autowired
    private MessageCollector messageCollector;

    @Autowired
    private ObjectMapper objectMapper;

    private MonthlyBudgetService service;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        messageCollector.forChannel(processor.output()).clear();
        service = new MonthlyBudgetService(this.monthlyBudgetRepository, this.messenger, this.processor);
    }

    @Test
    public void shouldUpdateMonthlyBudget() {
        LocalDateTime now = LocalDateTime.now();
        MonthlyBudgetId monthlyBudgetId = MonthlyBudgetId.of(now.getMonth(), (long) now.getYear());
        monthlyBudgetRepository.save(new MonthlyBudget(monthlyBudgetId, BigDecimal.TEN));

        Spend spend = new Spend("test", BigDecimal.TEN, now);
        NewSpendCreated event = new NewSpendCreated(now, spend);
        messenger.emit(NewSpendCreated.class, event);

        Optional<MonthlyBudget> budget = monthlyBudgetRepository.findById(monthlyBudgetId);

        budget
                .map(b -> assertThat(b.getTotalSpent()).isEqualTo(BigDecimal.TEN))
                .orElseThrow(() -> new AssertionError("Not found"));
    }

    @Test
    public void shouldEmitAnEvent() throws JsonProcessingException {
        LocalDateTime now = LocalDateTime.now();
        MonthlyBudgetId budgetId = MonthlyBudgetId.of(now);
        monthlyBudgetRepository.save(new MonthlyBudget(budgetId, BigDecimal.ZERO));

        service.onEvent(new NewSpendCreated(now, new Spend("", BigDecimal.TEN, now)));
        Message<?> message = messageCollector.forChannel(processor.output()).poll();

        Object eventJson = message.getPayload();
        MonthlyBudgetExceeded expectedEvent = new MonthlyBudgetExceeded(BigDecimal.ZERO, BigDecimal.TEN);
        String expectedJson = objectMapper.writeValueAsString(expectedEvent);

        assertThat(eventJson).isEqualTo(expectedJson);
    }
}