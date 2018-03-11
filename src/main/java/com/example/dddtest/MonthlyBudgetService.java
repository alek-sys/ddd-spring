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
import java.util.Collection;
import java.util.Collections;

@Component
public class MonthlyBudgetService extends BaseConnectedService {

    private final MonthlyBudgetRepository budgetRepository;

    public MonthlyBudgetService(MonthlyBudgetRepository budgetRepository, LocalMessenger messenger) {
        super(messenger);
        this.budgetRepository = budgetRepository;
    }

    @Transactional@Override
    void onEvent(Object event) {
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
    Collection<Class> supportedEvents() {
        return Collections.singletonList(NewSpendCreated.class);
    }
}
