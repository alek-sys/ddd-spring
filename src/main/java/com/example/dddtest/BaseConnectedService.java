package com.example.dddtest;

import com.example.dddtest.messaging.LocalMessenger;

import javax.annotation.PostConstruct;
import java.util.Collection;

abstract class BaseConnectedService {

    private final LocalMessenger messenger;

    protected BaseConnectedService(LocalMessenger messenger) {
        this.messenger = messenger;

        supportedEvents()
                .forEach(eventClass -> messenger.subscribe(eventClass, this::onEvent));
    }

    abstract void onEvent(Object event);

    abstract Collection<Class> supportedEvents();

    <T> void emit(Class<T> clazz, T event) {
        messenger.emit(clazz, event);
    }
}
