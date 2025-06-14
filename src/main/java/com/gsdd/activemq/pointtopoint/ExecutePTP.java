package com.gsdd.activemq.pointtopoint;

import com.gsdd.activemq.ActiveMQConstants;
import com.gsdd.activemq.BasicConsumer;
import com.gsdd.activemq.BasicProducer;
import com.gsdd.activemq.BrokerProducerConsumer;
import java.util.concurrent.Executors;

public class ExecutePTP {

  public static void main(String[] args) {
    Runnable consumer = () -> {
      BasicConsumer basicConsumer =
          new BasicConsumer(new BrokerProducerConsumer(), ActiveMQConstants.TIME_BETWEEN_MESSAGES);
      basicConsumer.postConstruct();
      basicConsumer.receiveMessage();
    };
    Runnable producer = () -> {
      BasicProducer basicProducer = new BasicProducer(new BrokerProducerConsumer());
      basicProducer.postConstruct();
      basicProducer.produceMessages();
    };
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      executor.execute(consumer);
      executor.execute(producer);
    }
  }
}
