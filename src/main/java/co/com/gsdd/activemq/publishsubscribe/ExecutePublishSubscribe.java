package co.com.gsdd.activemq.publishsubscribe;

import co.com.gsdd.activemq.ActiveMQConstants;
import co.com.gsdd.activemq.BasicConsumer;
import co.com.gsdd.activemq.BasicProducer;
import co.com.gsdd.activemq.BrokerPublishSubscribe;

public class ExecutePublishSubscribe {

    public static void main(String[] args) {
        Thread consumer = new Thread(
                () -> new BasicConsumer(new BrokerPublishSubscribe(), ActiveMQConstants.TIME_BETWEEN_MESSAGES)
                        .receiveMessage());
        Thread producer = new Thread(() -> new BasicProducer(new BrokerPublishSubscribe()).produceMessages());
        consumer.start();
        producer.start();
    }
}
