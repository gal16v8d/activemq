package co.com.gsdd.activemq;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicProducer {

    private static final int PACKAGES_TO_SEND = 200;
    private MessageProducer messageProducer;
    private final AbstractBrokerConfig configuration;

    public BasicProducer(AbstractBrokerConfig configuration) {
        this.configuration = configuration;
        try {
            this.configuration.connectToBroker();
            messageProducer = this.configuration.getSession().createProducer(this.configuration.getDestination());
            messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        } catch (JMSException e) {
            log.error("Error {}", e.getMessage(), e);
        }
    }

    public void produceMessages() {
        for (int i = 0; i < PACKAGES_TO_SEND; i++) {
            sendMessage("package #: " + i);
        }
        closeProducer();
        getConfiguration().closeResources();
    }

    private void sendMessage(String text) {
        try {
            TextMessage message = configuration.getSession().createTextMessage(text);
            messageProducer.send(message);
            log.info("Sending '{}'", message);
        } catch (JMSException e) {
            log.error("Error {}", e.getMessage(), e);
        }
    }

    private void closeProducer() {
        if (messageProducer != null) {
            try {
                messageProducer.close();
            } catch (JMSException e) {
                log.error("Error closing producer: {}", e.getMessage(), e);
            }
        }
    }

    public AbstractBrokerConfig getConfiguration() {
        return configuration;
    }

}
