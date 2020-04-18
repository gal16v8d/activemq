package co.com.gsdd.activemq;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicProducer {

    private MessageProducer messageProducer;
    private final AbstractBrokerConfig configuration;

    public BasicProducer(AbstractBrokerConfig configuration) {
        this.configuration = configuration;
        try {
            messageProducer = configuration.getSession().createProducer(configuration.getDestination());
            messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        } catch (JMSException e) {
            log.error("Error {}", e.getMessage(), e);
        }
    }

    public void sendMessage(String text) {
        try {
            TextMessage message = configuration.getSession().createTextMessage(text);
            messageProducer.send(message);
        } catch (JMSException e) {
            log.error("Error {}", e.getMessage(), e);
        }
    }

    public void closeProducer() {
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
