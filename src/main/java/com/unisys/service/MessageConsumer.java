package com.unisys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @JmsListener(destination = "employeeQueue")
    public void receiveMessage(String message) {
        logger.info("Received message: {}", message);
    }
}
