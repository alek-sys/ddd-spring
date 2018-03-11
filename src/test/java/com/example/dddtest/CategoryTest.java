package com.example.dddtest;

import com.example.dddtest.domain.Spend;
import com.example.dddtest.domain.SpendCategory;
import com.example.dddtest.domain.events.NewSpendCreated;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Java6Assertions.assertThat;

@SuppressWarnings("ALL")
public class CategoryTest {

	private SpendCategory wine;

	@Before
	public void setup() {
		wine = new SpendCategory("Wine");
	}

	@Test
	public void calculatesTotalSpent() {
		Spend burgundyWine = new Spend(
				"Maranges 1er Cru",
				BigDecimal.TEN,
				LocalDateTime.now()
		);

		wine.addSpend(burgundyWine);

		assertThat(wine.getTotalSpend()).isEqualTo(BigDecimal.TEN);
	}

	@Test
	public void createsNewSpendEvent() {
		Spend burgundyWine = new Spend(
				"Maranges 1er Cru",
				BigDecimal.TEN,
				LocalDateTime.now()
		);

		NewSpendCreated newSpendCreated = wine.addSpend(burgundyWine);

		assertThat(newSpendCreated.getNewSpend()).isEqualTo(burgundyWine);
	}
}
