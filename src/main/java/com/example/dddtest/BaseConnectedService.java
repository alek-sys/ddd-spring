package com.example.dddtest;

import com.example.dddtest.messaging.LocalMessenger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

abstract class BaseConnectedService<T> {

    @Autowired
    private LocalMessenger<T> localMessenger;

    abstract void onEvent(T event);

    @PostConstruct
    void subscribe() {
        localMessenger.subscribe(this::onEvent);
    }
}
