package com.gsdd.activemq;

public class BrokerPublishSubscribe extends AbstractBrokerConfig {

  @Override
  public DestinationType destinationType() {
    return DestinationType.TOPIC;
  }
}
