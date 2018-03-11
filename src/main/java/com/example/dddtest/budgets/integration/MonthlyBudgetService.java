package com.example.dddtest.budgets.integration;

import com.example.dddtest.budgets.domain.MonthlyBudget;
import com.example.dddtest.budgets.domain.MonthlyBudgetId;
import com.example.dddtest.spends.domain.Spend;
import com.example.dddtest.budgets.domain.events.MonthlyBudgetEvent;
import com.example.dddtest.spends.domain.events.NewSpendCreated;
import com.example.dddtest.messaging.LocalMessenger;
import com.example.dddtest.services.BaseConnectedService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;

@Component
public class MonthlyBudgetService extends BaseConnectedService {

    private final MonthlyBudgetRepository budgetRepository;

    public MonthlyBudgetService(MonthlyBudgetRepository budgetRepository, LocalMessenger messenger) {
        super(messenger);
        this.budgetRepository = budgetRepository;
    }

    @Transactional
    @Override
    public void onEvent(Object event) {
        final Spend spend = ((NewSpendCreated) event).getNewSpend();
        final MonthlyBudgetId id =
                MonthlyBudgetId.of(spend.getDateTime());

        MonthlyBudget monthlyBudget = budgetRepository
                .findById(id)
                .orElse(new MonthlyBudget(id, MonthlyBudget.DEFAULT_LIMIT));

        MonthlyBudgetEvent outputEvent = monthlyBudget.addSpend(spend);
        this.emit(MonthlyBudgetEvent.class, outputEvent);

        budgetRepository.save(monthlyBudget);
    }

    @Override
    protected Collection<Class> supportedEvents() {
        return Collections.singletonList(NewSpendCreated.class);
    }
}
