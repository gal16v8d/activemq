package co.com.gsdd.activemq.pointtopoint;

import co.com.gsdd.activemq.ActiveMQConstants;
import co.com.gsdd.activemq.BasicConsumer;
import co.com.gsdd.activemq.BasicProducer;
import co.com.gsdd.activemq.BrokerProducerConsumer;

public class ExecutePTP {

    public static void main(String[] args) {
        Thread consumer = new Thread(
                () -> new BasicConsumer(new BrokerProducerConsumer(), ActiveMQConstants.TIME_BETWEEN_MESSAGES)
                        .receiveMessage());
        Thread producer = new Thread(() -> new BasicProducer(new BrokerProducerConsumer()).produceMessages());
        consumer.start();
        producer.start();
    }

}
