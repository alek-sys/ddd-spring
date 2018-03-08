package com.example.dddtest;

import com.example.dddtest.domain.MonthlyBudget;
import com.example.dddtest.domain.MonthlyBudgetId;
import com.example.dddtest.domain.Spend;
import com.example.dddtest.domain.events.MonthlyBudgetEvent;
import com.example.dddtest.domain.events.NewSpendCreated;
import com.example.dddtest.messaging.LocalMessenger;
import com.example.dddtest.persistence.MonthlyBudgetRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class MonthlyBudgetService extends BaseConnectedService<NewSpendCreated> {

    private final LocalMessenger<MonthlyBudgetEvent> messenger;

    private final MonthlyBudgetRepository budgetRepository;

    public MonthlyBudgetService(LocalMessenger<MonthlyBudgetEvent> messenger, MonthlyBudgetRepository budgetRepository) {
        this.messenger = messenger;
        this.budgetRepository = budgetRepository;
    }

    @Transactional
    @Override
    void onEvent(NewSpendCreated event) {
        final Spend spend = event.getNewSpend();
        final MonthlyBudgetId id =
                MonthlyBudgetId.of(spend.getDateTime());

        MonthlyBudget monthlyBudget = budgetRepository
                .findById(id)
                .orElse(new MonthlyBudget(id, MonthlyBudget.DEFAULT_LIMIT));

        MonthlyBudgetEvent outputEvent = monthlyBudget.addSpend(spend);
        messenger.emit(outputEvent);

        budgetRepository.save(monthlyBudget);
    }
}
