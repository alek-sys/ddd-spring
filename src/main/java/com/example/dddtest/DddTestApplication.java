package com.example.dddtest;

import com.example.dddtest.domain.events.MonthlyBudgetEvent;
import com.example.dddtest.domain.events.NewSpendCreated;
import com.example.dddtest.messaging.LocalMessenger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DddTestApplication {

	@Bean
	public LocalMessenger<NewSpendCreated> spendCreatedLocalMessenger() {
		return new LocalMessenger<>();
	}

	@Bean
	public LocalMessenger<MonthlyBudgetEvent> monthlyBudgetEventLocalMessenger() {
		return new LocalMessenger<>();
	}

	public static void main(String[] args) {
		SpringApplication.run(DddTestApplication.class, args);
	}
}
