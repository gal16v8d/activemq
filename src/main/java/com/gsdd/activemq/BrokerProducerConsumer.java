package com.gsdd.activemq;

public class BrokerProducerConsumer extends AbstractBrokerConfig {

  @Override
  public DestinationType destinationType() {
    return DestinationType.QUEUE;
  }

}
