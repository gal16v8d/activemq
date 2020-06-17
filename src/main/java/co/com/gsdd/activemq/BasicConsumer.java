package co.com.gsdd.activemq;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicConsumer {

    private AbstractBrokerConfig configuration;
    private int timeBetweenMessages;

    public BasicConsumer(AbstractBrokerConfig configuration, int timeBetweenMessages) {
        this.configuration = configuration;
        this.timeBetweenMessages = timeBetweenMessages;
        this.configuration.connectToBroker();
    }

    public void receiveMessage() {
        try {
            configuration.getSession().createConsumer(configuration.getDestination())
                    .setMessageListener(new ProcessorMessage(timeBetweenMessages));
        } catch (Exception e) {
            log.error("Error receiving consumer: {}", e.getMessage(), e);
        }
    }
}
