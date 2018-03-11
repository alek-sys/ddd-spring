package com.example.dddtest.spends.integration;

import com.example.dddtest.services.BaseConnectedService;
import com.example.dddtest.spends.domain.Spend;
import com.example.dddtest.spends.domain.SpendCategoryId;
import com.example.dddtest.spends.domain.events.NewSpendCreated;
import com.example.dddtest.messaging.LocalMessenger;
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
    protected void onEvent(Object event) {

    }

    @Override
    protected Collection<Class> supportedEvents() {
        return Collections.emptyList();
    }
}
