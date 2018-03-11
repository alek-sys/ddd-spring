package com.example.dddtest.services;

import com.example.dddtest.domain.Spend;
import com.example.dddtest.domain.SpendCategoryId;
import com.example.dddtest.domain.events.NewSpendCreated;
import com.example.dddtest.messaging.LocalMessenger;
import com.example.dddtest.persistence.SpendsRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Component
public class SpendsService extends BaseConnectedService {
    private final SpendsRepository spendsRepository;

    public SpendsService(SpendsRepository spendsRepository, LocalMessenger messenger) {
        super(messenger);
        this.spendsRepository = spendsRepository;
    }

    @Transactional
    public Long addSpend(SpendCategoryId categoryId, Spend spend) {
        spend.linkToCategory(categoryId);
        spendsRepository.save(spend);
        this.emit(NewSpendCreated.class, new NewSpendCreated(LocalDateTime.now(), spend));

        return spend.getId();
    }

    @Override
    void onEvent(Object event) {

    }

    @Override
    Collection<Class> supportedEvents() {
        return Collections.emptyList();
    }
}
