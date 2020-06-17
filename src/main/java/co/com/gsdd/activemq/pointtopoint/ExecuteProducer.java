package co.com.gsdd.activemq.pointtopoint;

import co.com.gsdd.activemq.BasicProducer;
import co.com.gsdd.activemq.BrokerProducerConsumer;

public class ExecuteProducer {

    public static void main(String[] args) {
        new BasicProducer(new BrokerProducerConsumer()).produceMessages();
    }

}
