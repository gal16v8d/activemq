package co.com.gsdd.activemq.publishsubscribe;

import co.com.gsdd.activemq.BasicProducer;
import co.com.gsdd.activemq.BrokerPublishSubscribe;

public class ExecutePublisher {

    public static void main(String[] args) {
        new BasicProducer(new BrokerPublishSubscribe()).produceMessages();
    }

}
