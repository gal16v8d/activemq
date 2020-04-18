package co.com.gsdd.activemq.publishsubscribe;

import co.com.gsdd.activemq.AbstractGeneratorProducer;
import co.com.gsdd.activemq.BasicProducer;
import co.com.gsdd.activemq.BrokerPublishSubscribe;

public class ExecutePublisher extends AbstractGeneratorProducer {

    public static void main(String[] args) {
        BasicProducer publisher = new BasicProducer(BrokerPublishSubscribe.getInstance());
        new ExecutePublisher().produceMessages(publisher);
    }

}
