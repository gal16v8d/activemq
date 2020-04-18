package co.com.gsdd.activemq;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BrokerPublishSubscribe extends AbstractBrokerConfig {

    private static final BrokerPublishSubscribe INSTANCE = new BrokerPublishSubscribe();

    @Override
    public DestinationType destinationType() {
        return DestinationType.TOPIC;
    }

    public static BrokerPublishSubscribe getInstance() {
        return INSTANCE;
    }

}
