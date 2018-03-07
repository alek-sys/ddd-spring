package com.example.dddtest;

import com.example.dddtest.domain.Spend;
import com.example.dddtest.domain.SpendCategoryId;
import com.example.dddtest.domain.events.NewSpendCreated;
import com.example.dddtest.messaging.LocalMessenger;
import com.example.dddtest.persistence.SpendsRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Component
public class SpendsService {
    private final SpendsRepository spendsRepository;
    private final LocalMessenger<NewSpendCreated> messenger;

    public SpendsService(SpendsRepository entityManager, LocalMessenger<NewSpendCreated> messenger) {
        this.spendsRepository = entityManager;
        this.messenger = messenger;
    }

    @Transactional
    public Long addSpend(SpendCategoryId categoryId, Spend spend) {
        spend.linkToCategory(categoryId);
        spendsRepository.save(spend);
        messenger.emit(new NewSpendCreated(LocalDateTime.now(), spend));

        return spend.getId();
    }
}
