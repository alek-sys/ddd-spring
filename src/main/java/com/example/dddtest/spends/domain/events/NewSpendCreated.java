package com.example.dddtest.spends.domain.events;

import com.example.dddtest.spends.domain.Spend;

import java.time.LocalDateTime;

public class NewSpendCreated {
	private final LocalDateTime eventDate;
	private final Spend newSpend;

	public NewSpendCreated(LocalDateTime eventDate, Spend newSpend) {
		this.eventDate = eventDate;
		this.newSpend = newSpend;
	}

	public LocalDateTime getEventDate() {
		return eventDate;
	}

	public Spend getNewSpend() {
		return newSpend;
	}
}
