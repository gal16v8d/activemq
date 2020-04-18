package co.com.gsdd.activemq;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BrokerProducerConsumer extends AbstractBrokerConfig {

    private static final BrokerProducerConsumer INSTANCE = new BrokerProducerConsumer();

    @Override
    public DestinationType destinationType() {
        return DestinationType.QUEUE;
    }

    public static BrokerProducerConsumer getInstance() {
        return INSTANCE;
    }

}
