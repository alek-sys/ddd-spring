package com.example.dddtest;

import com.example.dddtest.domain.Spend;
import com.example.dddtest.domain.SpendCategory;
import com.example.dddtest.domain.SpendCategoryId;
import com.example.dddtest.persistence.SpendCategoriesRepository;
import com.example.dddtest.persistence.SpendsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpendsServiceTest {

    @Autowired
    private SpendCategoriesRepository spendCategoriesRepository;

    @Autowired
    private SpendsRepository spendsRepository;


    @Autowired
    private SpendsService service;

    @Before
    public void setUp() {
        spendCategoriesRepository.save(new SpendCategory("Wine"));
    }

    @Test
    public void shouldUpdateACategory() {
        service.addSpend(SpendCategoryId.of("wine"),
                new Spend("test", BigDecimal.TEN, LocalDateTime.now()));

        service.addSpend(SpendCategoryId.of("wine"),
                new Spend("test", BigDecimal.ONE, LocalDateTime.now()));

        SpendCategory category = spendCategoriesRepository.findById(SpendCategoryId.of("wine")).get();

        assertThat(category.getTotalSpend()).isEqualTo(BigDecimal.valueOf(11).setScale(2));
    }

    @Test
    public void shouldCreateASpend() {
        Spend spend = new Spend("test", BigDecimal.TEN.setScale(2), LocalDateTime.now());
        Long id = service.addSpend(SpendCategoryId.of("wine"), spend);

        Spend actualSpend = spendsRepository.findById(id).get();

        assertThat(actualSpend).isEqualToIgnoringGivenFields(spend, "id");
    }
}