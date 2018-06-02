package com.example.dddtest.budgets.integration;

import com.example.dddtest.budgets.domain.MonthlyBudgetId;
import com.example.dddtest.budgets.domain.events.MonthlyBudgetEvent;
import com.example.dddtest.messaging.LocalMessenger;
import com.example.dddtest.services.BaseConnectedService;
import com.example.dddtest.spends.domain.Spend;
import com.example.dddtest.spends.domain.events.NewSpendCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;

@Component
public class MonthlyBudgetService extends BaseConnectedService {

    private final MonthlyBudgetRepository budgetRepository;

    private final Processor processor;

    public MonthlyBudgetService(MonthlyBudgetRepository budgetRepository, LocalMessenger messenger, Processor processor) {
        super(messenger);
        this.budgetRepository = budgetRepository;
        this.processor = processor;
    }

    @Transactional
    @Override
    public void onEvent(Object event) {
        final Spend spend = ((NewSpendCreated) event).getNewSpend();
        final MonthlyBudgetId id =
                MonthlyBudgetId.of(spend.getDateTime());

        budgetRepository
                .findById(id)
                .ifPresent(monthlyBudget -> {
                    MonthlyBudgetEvent outputEvent = monthlyBudget.addSpend(spend);
                    this.processor.output().send(MessageBuilder.withPayload(outputEvent).build());

                    budgetRepository.save(monthlyBudget);
                });
    }

    @Override
    protected Collection<Class> supportedEvents() {
        return Collections.singletonList(NewSpendCreated.class);
    }
}
