package com.example.dddtest.services;

import com.example.dddtest.domain.Spend;
import com.example.dddtest.domain.SpendCategory;
import com.example.dddtest.domain.events.NewSpendCreated;
import com.example.dddtest.messaging.LocalMessenger;
import com.example.dddtest.persistence.SpendCategoriesRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
public class CategoriesService extends BaseConnectedService {

    private final SpendCategoriesRepository categoriesRepository;

    public CategoriesService(SpendCategoriesRepository entityManager, LocalMessenger messenger) {
        super(messenger);
        this.categoriesRepository = entityManager;
    }

    @Transactional
    public void onNewSpend(Spend spend) {
        Optional<SpendCategory> spendCategory = categoriesRepository.findById(spend.getCategoryId());
        spendCategory.ifPresent(c -> c.addSpend(spend));
    }

    @Override
    void onEvent(Object event) {
        final NewSpendCreated e = (NewSpendCreated) event;
        this.onNewSpend(e.getNewSpend());
    }

    @Override
    Collection<Class> supportedEvents() {
        return Collections.singletonList(NewSpendCreated.class);
    }
}
