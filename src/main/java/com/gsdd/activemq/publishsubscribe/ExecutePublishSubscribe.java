package com.gsdd.activemq.publishsubscribe;

import com.gsdd.activemq.ActiveMQConstants;
import com.gsdd.activemq.BasicConsumer;
import com.gsdd.activemq.BasicProducer;
import com.gsdd.activemq.BrokerPublishSubscribe;

public class ExecutePublishSubscribe {

  public static void main(String[] args) {
    Thread consumer = new Thread(() -> new BasicConsumer(new BrokerPublishSubscribe(),
        ActiveMQConstants.TIME_BETWEEN_MESSAGES).receiveMessage());
    Thread producer =
        new Thread(() -> new BasicProducer(new BrokerPublishSubscribe()).produceMessages());
    consumer.start();
    producer.start();
  }
}
