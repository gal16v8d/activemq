package com.gsdd.activemq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BrokerPublishSubscribeTest {

  @Spy
  private BrokerPublishSubscribe brokerPublishSubscribe;

  @Test
  void testDestinationType() {
    Assertions.assertEquals(DestinationType.TOPIC, brokerPublishSubscribe.destinationType());
  }
}
