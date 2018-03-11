package com.example.dddtest.messaging;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalMessengerTest {

    @Test
    public void emitsEventsToSubscribers() {
        LocalMessenger messenger = new LocalMessenger();

        messenger.subscribe(String.class, v -> assertThat(v).isEqualTo("hello"));

        messenger.emit(String.class, "hello");
    }
}