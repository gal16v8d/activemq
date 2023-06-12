package com.gsdd.activemq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BrokerProducerConsumerTest {

  @Spy
  private BrokerProducerConsumer brokerProducerConsumer;

  @Test
  void testDestinationType() {
    Assertions.assertEquals(DestinationType.QUEUE, brokerProducerConsumer.destinationType());
  }
}
