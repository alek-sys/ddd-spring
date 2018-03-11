package com.example.dddtest;

import com.example.dddtest.domain.Spend;
import com.example.dddtest.domain.SpendCategory;
import com.example.dddtest.domain.SpendCategoryId;
import com.example.dddtest.domain.events.NewSpendCreated;
import com.example.dddtest.persistence.SpendCategoriesRepository;
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
    public void setUp() {
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