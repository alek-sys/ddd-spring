package com.example.dddtest.notifications.integration;

import com.example.dddtest.budgets.domain.events.MonthlyBudgetExceeded;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    private Processor processor;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldSendAMessageOnBudgetExceeded() throws JsonProcessingException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        System.setOut(ps);

        MonthlyBudgetExceeded event = new MonthlyBudgetExceeded(ONE, TEN);
        String eventJson = objectMapper.writeValueAsString(event);

        processor.input().send(MessageBuilder.withPayload(eventJson).build());

        String outString = bos.toString();

        assertThat(outString).isEqualTo("Oh-la-la, budget of £1 exceeded!");
    }
}