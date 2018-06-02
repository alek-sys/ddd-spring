package com.example.dddtest.spends.integration;

import com.example.dddtest.ServiceIntegrationTest;
import com.example.dddtest.spends.domain.Spend;
import com.example.dddtest.spends.domain.SpendCategory;
import com.example.dddtest.spends.domain.SpendCategoryId;
import com.example.dddtest.spends.domain.events.NewSpendCreated;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Java6Assertions.assertThat;

@DataJpaTest
public class CategoriesServiceTest extends ServiceIntegrationTest {

    @Autowired
    private SpendCategoriesRepository spendCategoriesRepository;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        CategoriesService categoriesService =
                new CategoriesService(spendCategoriesRepository, messenger);
        spendCategoriesRepository.save(new SpendCategory("Test"));
    }

    @Test
    public void shouldUpdateCategoryOnEvent() {
        final SpendCategoryId categoryId = SpendCategoryId.of("test");
        final Spend spend = new Spend("test", BigDecimal.TEN, LocalDateTime.now());
        spend.linkToCategory(categoryId);

        NewSpendCreated newSpendCreated = new NewSpendCreated(LocalDateTime.now(), spend);

        messenger.emit(NewSpendCreated.class, newSpendCreated);
        messenger.emit(NewSpendCreated.class, newSpendCreated);

        SpendCategory spendCategory =
                spendCategoriesRepository.findById(categoryId).get();

        assertThat(spendCategory.getTotalSpend()).isEqualTo(BigDecimal.valueOf(20));
    }
}