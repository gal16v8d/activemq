package co.com.gsdd.activemq.publishsubscribe;

import co.com.gsdd.activemq.ActiveMQConstants;
import co.com.gsdd.activemq.BasicConsumer;
import co.com.gsdd.activemq.BrokerPublishSubscribe;

public class ExecuteSubscriber {

    public static void main(String[] args) {
        new BasicConsumer(new BrokerPublishSubscribe(), ActiveMQConstants.TIME_BETWEEN_MESSAGES).receiveMessage();
    }
}
