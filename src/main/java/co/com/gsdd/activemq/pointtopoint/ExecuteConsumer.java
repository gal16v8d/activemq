package co.com.gsdd.activemq.pointtopoint;

import co.com.gsdd.activemq.ActiveMQConstants;
import co.com.gsdd.activemq.BasicConsumer;
import co.com.gsdd.activemq.BrokerProducerConsumer;

public class ExecuteConsumer {

    public static void main(String[] args) {
        new BasicConsumer(BrokerProducerConsumer.getInstance(), ActiveMQConstants.TIME_BETWEEN_MESSAGES)
                .receiveMessage();
    }
}
