package com.example.dddtest.messaging;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalMessengerTest {

    @Test
    public void emitsEventsToSubscribers() {
        LocalMessenger<String> messenger = new LocalMessenger<>();

        messenger.subscribe(v -> assertThat(v).isEqualTo("hello"));

        messenger.emit("hello");
    }
}