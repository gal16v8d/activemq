package co.com.gsdd.activemq.pointtopoint;

import co.com.gsdd.activemq.AbstractGeneratorProducer;
import co.com.gsdd.activemq.BasicProducer;
import co.com.gsdd.activemq.BrokerProducerConsumer;

public class ExecuteProducer extends AbstractGeneratorProducer {

    public static void main(String[] args) {
        BasicProducer producer = new BasicProducer(BrokerProducerConsumer.getInstance());
        new ExecuteProducer().produceMessages(producer);
    }

}
