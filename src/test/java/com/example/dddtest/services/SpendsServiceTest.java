package com.example.dddtest.services;

import com.example.dddtest.domain.Spend;
import com.example.dddtest.domain.SpendCategory;
import com.example.dddtest.domain.SpendCategoryId;
import com.example.dddtest.domain.events.NewSpendCreated;
import com.example.dddtest.persistence.SpendCategoriesRepository;
import com.example.dddtest.persistence.SpendsRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Java6Assertions.assertThat;

@DataJpaTest
public class SpendsServiceTest extends ServiceIntegrationTest {

    @Autowired
    private SpendCategoriesRepository spendCategoriesRepository;

    @Autowired
    private SpendsRepository spendsRepository;

    private SpendsService service;

    @Before
    public void setUp() {
        spendCategoriesRepository.save(new SpendCategory("Wine"));
        service = new SpendsService(spendsRepository, messenger);
    }

    @Test
    public void shouldEmitAnEvent() {
        Spend spend = new Spend("test", BigDecimal.TEN, LocalDateTime.now());

        messenger.subscribe(NewSpendCreated.class, newSpendCreated ->
                assertThat(newSpendCreated.getNewSpend()).isEqualTo(spend));

        service.addSpend(SpendCategoryId.of("wine"),
                spend);
    }

    @Test
    public void shouldCreateASpend() {
        Spend spend = new Spend("test", BigDecimal.TEN.setScale(2), LocalDateTime.now());
        Long id = service.addSpend(SpendCategoryId.of("wine"), spend);

        Spend actualSpend = spendsRepository.findById(id).get();

        assertThat(actualSpend).isEqualToIgnoringGivenFields(spend, "id");
    }
}