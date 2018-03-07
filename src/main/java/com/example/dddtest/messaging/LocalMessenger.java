package com.example.dddtest.messaging;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class LocalMessenger<T> {

    private static class EventSubscription<T> {
        private final Consumer<T> subscriber;
        EventSubscription(Consumer<T> consumer) {
            this.subscriber = consumer;
        }

        @Subscribe
        void onEvent(T data) {
            subscriber.accept(data);
        }
    }

    private EventBus eventBus = new EventBus();

    public void emit(T event) {
        eventBus.post(event);
    }

    public void subscribe(Consumer<T> handler) {
        eventBus.register(new EventSubscription<>(handler));
    }
}
