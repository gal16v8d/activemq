package com.gsdd.activemq.publishsubscribe;

import com.gsdd.activemq.ActiveMQConstants;
import com.gsdd.activemq.BasicConsumer;
import com.gsdd.activemq.BasicProducer;
import com.gsdd.activemq.BrokerPublishSubscribe;
import java.util.concurrent.Executors;

public class ExecutePublishSubscribe {

  public static void main(String[] args) {
    Runnable consumer = () -> {
      BasicConsumer basicConsumer =
          new BasicConsumer(new BrokerPublishSubscribe(), ActiveMQConstants.TIME_BETWEEN_MESSAGES);
      basicConsumer.postConstruct();
      basicConsumer.receiveMessage();
    };
    Runnable producer = () -> {
      BasicProducer basicProducer = new BasicProducer(new BrokerPublishSubscribe());
      basicProducer.postConstruct();
      basicProducer.produceMessages();
    };
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      executor.execute(consumer);
      executor.execute(producer);
    }
  }
}
