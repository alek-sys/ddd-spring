package com.example.dddtest.messaging;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import com.google.common.eventbus.EventBus;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class LocalMessenger {

    private Multimap<Class, Consumer> events = HashMultimap.create();

    public <T> void emit(Class<T> clazz, T event) {
        events.get(clazz).forEach(handler -> handler.accept(event));
    }

    public <T> void subscribe(Class<T> clazz, Consumer<T> handler) {
        events.put(clazz, handler);
    }

    public void reset() {
        events.clear();
    }
}
