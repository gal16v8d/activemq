package com.gsdd.activemq.pointtopoint;

import com.gsdd.activemq.ActiveMQConstants;
import com.gsdd.activemq.BasicConsumer;
import com.gsdd.activemq.BasicProducer;
import com.gsdd.activemq.BrokerProducerConsumer;

public class ExecutePTP {

  public static void main(String[] args) {
    Thread consumer = new Thread(() -> new BasicConsumer(new BrokerProducerConsumer(),
        ActiveMQConstants.TIME_BETWEEN_MESSAGES).receiveMessage());
    Thread producer =
        new Thread(() -> new BasicProducer(new BrokerProducerConsumer()).produceMessages());
    consumer.start();
    producer.start();
  }

}
