package com.example.dddtest.messaging;

import com.google.common.eventbus.Subscribe;

import java.util.function.Consumer;

public class EventSubscription<T>  {
    private final Consumer<T> subscriber;
    EventSubscription(Consumer<T> consumer) {
        this.subscriber = consumer;
    }

    @Subscribe
    void onEvent(T data) {
        subscriber.accept(data);
    }
}
