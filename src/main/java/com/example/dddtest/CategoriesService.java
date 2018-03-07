package com.example.dddtest;

import com.example.dddtest.domain.Spend;
import com.example.dddtest.domain.SpendCategory;
import com.example.dddtest.domain.events.NewSpendCreated;
import com.example.dddtest.persistence.SpendCategoriesRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class CategoriesService extends BaseConnectedService<NewSpendCreated> {

    private final SpendCategoriesRepository categoriesRepository;

    public CategoriesService(SpendCategoriesRepository entityManager) {
        this.categoriesRepository = entityManager;
    }

    @Transactional
    public void onNewSpend(Spend spend) {
        Optional<SpendCategory> spendCategory = categoriesRepository.findById(spend.getCategoryId());
        spendCategory.ifPresent(c -> c.addSpend(spend));
    }

    @Override
    void onEvent(NewSpendCreated event) {
        this.onNewSpend(event.getNewSpend());
    }
}
