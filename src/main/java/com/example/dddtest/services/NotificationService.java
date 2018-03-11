package com.example.dddtest.services;

import com.example.dddtest.budgets.domain.events.MonthlyBudgetTotalIncreased;
import com.example.dddtest.messaging.LocalMessenger;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class NotificationService extends BaseConnectedService {

    public NotificationService(LocalMessenger messenger) {
        super(messenger);
    }

    @Override
    protected void onEvent(Object event) {
        if (event instanceof MonthlyBudgetTotalIncreased) {
            MonthlyBudgetTotalIncreased m = (MonthlyBudgetTotalIncreased) event;
            System.out.printf("Oh la la! New total is %s", m.getTotal());
        }
    }

    @Override
    protected Collection<Class> supportedEvents() {
        return Collections.singletonList(MonthlyBudgetTotalIncreased.class);
    }
}
