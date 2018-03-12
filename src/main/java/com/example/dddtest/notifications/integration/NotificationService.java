package com.example.dddtest.notifications.integration;

import com.example.dddtest.budgets.domain.events.MonthlyBudgetExceeded;
import com.example.dddtest.budgets.domain.events.MonthlyBudgetTotalIncreased;
import com.example.dddtest.messaging.LocalMessenger;
import com.example.dddtest.services.BaseConnectedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Processor;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;

@Component
public class NotificationService  {

    public NotificationService() {
    }

    @StreamListener(value = Sink.INPUT)
    protected void sendNotification(MonthlyBudgetExceeded event) {
        System.out.printf("Oh-la-la, budget of £%d exceeded!", event.getLimit().intValue());
    }
}
