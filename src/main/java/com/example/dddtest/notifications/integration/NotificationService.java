package com.example.dddtest.notifications.integration;

import com.example.dddtest.budgets.domain.events.MonthlyBudgetExceeded;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
public class NotificationService  {

    public NotificationService() {
    }

    @StreamListener(value = Sink.INPUT)
    protected void sendNotification(MonthlyBudgetExceeded event) {
        System.out.printf("Oh-la-la, budget of £%d exceeded!", event.getLimit().intValue());
    }
}
