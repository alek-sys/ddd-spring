package com.example.dddtest.domain.events;

import com.example.dddtest.domain.Spend;

import java.time.LocalDateTime;

public class NewSpendCreated {
	private LocalDateTime eventDate;
	private Spend newSpend;

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
