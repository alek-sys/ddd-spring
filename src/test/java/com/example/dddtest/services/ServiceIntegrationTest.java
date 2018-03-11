package com.example.dddtest.services;

import com.example.dddtest.messaging.LocalMessenger;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public abstract class ServiceIntegrationTest {

    LocalMessenger messenger = new LocalMessenger();

    @After
    public void setUp() {
        messenger.reset();
    }
}
