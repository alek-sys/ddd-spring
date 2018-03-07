package com.example.dddtest.domain;

import com.example.dddtest.domain.events.NewSpendCreated;

import javax.annotation.Nonnull;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class SpendCategory {

	@EmbeddedId
	SpendCategoryId id;
	String name;
	BigDecimal total = BigDecimal.ZERO;

	private SpendCategory() {}

	public SpendCategory(String name) {
		this.name = name;
		this.id = new SpendCategoryId(name.toLowerCase());
	}

	public BigDecimal getTotalSpend() {
		return total;
	}

	public NewSpendCreated addSpend(@Nonnull Spend spend) {
		spend.linkToCategory(this.id);

		this.total = this.total.add(spend.amount);

		return new NewSpendCreated(LocalDateTime.now(), spend);
	}
}
