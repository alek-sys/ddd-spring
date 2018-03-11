package com.example.dddtest.services;

import com.example.dddtest.domain.events.MonthlyBudgetTotalIncreased;
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
    void onEvent(Object event) {
        if (event instanceof MonthlyBudgetTotalIncreased) {
            MonthlyBudgetTotalIncreased m = (MonthlyBudgetTotalIncreased) event;
            System.out.printf("Oh la la! New total is %s", m.getTotal());
        }
    }

    @Override
    Collection<Class> supportedEvents() {
        return Collections.singletonList(MonthlyBudgetTotalIncreased.class);
    }
}
