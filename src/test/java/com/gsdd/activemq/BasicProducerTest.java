package com.gsdd.activemq;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.spy;

import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BasicProducerTest {

  private BasicProducer basicProducer;
  @Mock
  private BrokerProducerConsumer brokerProducerConsumer;
  @Mock
  private Session session;
  @Mock
  private MessageProducer producer;

  @BeforeEach
  void setUp() throws JMSException {
    willDoNothing().given(brokerProducerConsumer).connectToBroker();
    willReturn(session).given(brokerProducerConsumer).getSession();
    willReturn(producer).given(session).createProducer(any());
    willDoNothing().given(producer).setDeliveryMode(DeliveryMode.PERSISTENT);
    basicProducer = spy(new BasicProducer(brokerProducerConsumer));
    basicProducer.postConstruct();
  }

  @Test
  void testInit() throws JMSException {
    then(producer).should().setDeliveryMode(DeliveryMode.PERSISTENT);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void testSendMessage(boolean withException, @Mock TextMessage txtMsg) throws JMSException {
    willReturn(session).given(brokerProducerConsumer).getSession();
    willReturn(txtMsg).given(session).createTextMessage(Mockito.anyString());
    if (withException) {
      willThrow(new JMSException("error")).given(producer).send(txtMsg);
    } else {
      willDoNothing().given(producer).send(txtMsg);
    }
    willThrow(new JMSException("error")).given(producer).close();
    willDoNothing().given(brokerProducerConsumer).closeResources();
    basicProducer.produceMessages();
    then(producer).should(atLeastOnce()).send(txtMsg);
    then(producer).should(atLeastOnce()).close();
  }
}
