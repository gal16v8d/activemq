package com.gsdd.activemq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BasicConsumer {

    private final AbstractBrokerConfig configuration;
    private final int timeBetweenMessages;

    public void postConstruct() {
        this.configuration.connectToBroker();
    }

    public void receiveMessage() {
        try {
            configuration
                    .getSession()
                    .createConsumer(configuration.getDestination())
                    .setMessageListener(new ProcessorMessage(timeBetweenMessages));
        } catch (Exception e) {
            log.error("Error receiving consumer: {}", e.getMessage(), e);
        }
    }
}
