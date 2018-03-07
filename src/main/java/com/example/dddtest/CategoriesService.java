package com.example.dddtest;

import com.example.dddtest.domain.Spend;
import com.example.dddtest.domain.SpendCategory;
import com.example.dddtest.domain.events.NewSpendCreated;
import com.example.dddtest.messaging.LocalMessenger;
import com.example.dddtest.persistence.SpendCategoriesRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class CategoriesService {

    private final LocalMessenger<NewSpendCreated> messenger;
    private final SpendCategoriesRepository categoriesRepository;

    public CategoriesService(
            LocalMessenger<NewSpendCreated> messenger,
            SpendCategoriesRepository entityManager) {
        this.messenger = messenger;
        this.categoriesRepository = entityManager;
    }


    @Transactional
    public void onNewSpend(NewSpendCreated event) {
        Spend newSpend = event.getNewSpend();
        Optional<SpendCategory> spendCategory = categoriesRepository.findById(newSpend.getCategoryId());
        spendCategory.ifPresent(c -> c.addSpend(newSpend));
    }

    @PostConstruct
    void subscribe() {
        this.messenger.subscribe(this::onNewSpend);
    }
}
