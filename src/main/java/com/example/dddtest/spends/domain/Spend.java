package com.example.dddtest.spends.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Spend {

	@Id
	@GeneratedValue
	private Long id;

	private LocalDateTime dateTime;
	BigDecimal amount;
	private String description;

	private SpendCategoryId categoryId;

	private Spend() {}

	public Spend(String description, BigDecimal amount, LocalDateTime dateTime) {
		this.description = description;
		this.amount = amount;
		this.dateTime = dateTime;
	}

	public Long getId() {
		return id;
	}

	public void linkToCategory(SpendCategoryId id) {
		this.categoryId = id;
	}

	public SpendCategoryId getCategoryId() {
		return categoryId;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}
}
