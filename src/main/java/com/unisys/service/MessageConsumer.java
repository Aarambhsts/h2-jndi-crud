package com.unisys.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @JmsListener(destination = "employeeQueue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
