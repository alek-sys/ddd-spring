package com.example.dddtest;

import com.example.dddtest.domain.Spend;
import com.example.dddtest.domain.SpendCategory;
import com.example.dddtest.domain.SpendCategoryId;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class SpendsService {
    private final EntityManager entityManager;

    public SpendsService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Optional<Long> addSpendToCategory(SpendCategoryId categoryId, Spend spend) {
        Optional<SpendCategory> category =
                Optional.ofNullable(entityManager.find(SpendCategory.class, categoryId));

        return category
                .map(c -> c.addSpend(spend))
                .map(e -> entityManager.merge(e.getNewSpend()))
                .map(Spend::getId);
    }
}
