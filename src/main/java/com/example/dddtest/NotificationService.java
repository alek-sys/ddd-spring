package com.example.dddtest;

import com.example.dddtest.domain.events.MonthlyBudgetEvent;
import com.example.dddtest.domain.events.MonthlyBudgetTotalIncreased;
import org.springframework.stereotype.Component;

@Component
public class NotificationService extends BaseConnectedService<MonthlyBudgetEvent> {

    @Override
    void onEvent(MonthlyBudgetEvent event) {
        if (event instanceof MonthlyBudgetTotalIncreased) {
            MonthlyBudgetTotalIncreased m = (MonthlyBudgetTotalIncreased) event;
            System.out.printf("Oh la la! New total is %s", m.getTotal());
        }
    }
}
