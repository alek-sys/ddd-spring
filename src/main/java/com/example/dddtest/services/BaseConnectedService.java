package com.example.dddtest.services;

import com.example.dddtest.messaging.LocalMessenger;

import java.util.Collection;

public abstract class BaseConnectedService {

    private final LocalMessenger messenger;

    protected BaseConnectedService(LocalMessenger messenger) {
        this.messenger = messenger;

        supportedEvents()
                .forEach(eventClass -> messenger.subscribe(eventClass, this::onEvent));
    }

    protected abstract void onEvent(Object event);

    protected abstract Collection<Class> supportedEvents();

    protected <T> void emit(Class<T> clazz, T event) {
        messenger.emit(clazz, event);
    }
}
