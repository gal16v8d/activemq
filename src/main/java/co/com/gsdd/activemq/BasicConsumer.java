package co.com.gsdd.activemq;

import javax.jms.JMSException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicConsumer {

    private AbstractBrokerConfig configuration;
    private int timeBetweenMessages;

    public BasicConsumer(AbstractBrokerConfig configuration, int timeBetweenMessages) {
        this.configuration = configuration;
        this.timeBetweenMessages = timeBetweenMessages;
    }

    public void receiveMessage() {
        try {
            configuration.getSession().createConsumer(configuration.getDestination())
                    .setMessageListener(new ProcessorMessage(timeBetweenMessages));
        } catch (JMSException e) {
            log.error("Error receiving consumer: {}", e.getMessage(), e);
        }
    }
}
