package com.example.dddtest.spends.integration;

import com.example.dddtest.services.BaseConnectedService;
import com.example.dddtest.spends.domain.Spend;
import com.example.dddtest.spends.domain.SpendCategory;
import com.example.dddtest.spends.domain.events.NewSpendCreated;
import com.example.dddtest.messaging.LocalMessenger;
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
    protected void onEvent(Object event) {
        final NewSpendCreated e = (NewSpendCreated) event;
        this.onNewSpend(e.getNewSpend());
    }

    @Override
    protected Collection<Class> supportedEvents() {
        return Collections.singletonList(NewSpendCreated.class);
    }
}
