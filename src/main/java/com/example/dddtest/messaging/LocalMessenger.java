package com.example.dddtest.messaging;

import com.google.common.eventbus.EventBus;

import java.util.function.Consumer;

public class LocalMessenger<T> {

    private EventBus eventBus = new EventBus();

    public void emit(T event) {
        eventBus.post(event);
    }

    public EventSubscription<T> subscribe(Consumer<T> handler) {
        EventSubscription<T> subscription = new EventSubscription<>(handler);
        eventBus.register(subscription);

        return subscription;
    }

    public void unsubscribe(EventSubscription<T> subscription) {
        eventBus.unregister(subscription);
    }
}
