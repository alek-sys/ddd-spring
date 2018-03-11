package com.example.dddtest.spends.domain;

import com.example.dddtest.spends.domain.events.NewSpendCreated;

import javax.annotation.Nonnull;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class SpendCategory {

	@EmbeddedId
	private
	SpendCategoryId id;
	private String name;
	private BigDecimal total = BigDecimal.ZERO;

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
