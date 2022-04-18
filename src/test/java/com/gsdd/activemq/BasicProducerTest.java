package com.gsdd.activemq;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BasicProducerTest {

    private BasicProducer basicProducer;
    @Mock private BrokerProducerConsumer brokerProducerConsumer;
    @Mock private Session session;
    @Mock private MessageProducer producer;

    @BeforeEach
    void setUp() throws JMSException {
        MockitoAnnotations.openMocks(this);
        Mockito.doNothing().when(brokerProducerConsumer).connectToBroker();
        Mockito.doReturn(session).when(brokerProducerConsumer).getSession();
        Mockito.doReturn(producer).when(session).createProducer(Mockito.any());
        Mockito.doNothing().when(producer).setDeliveryMode(DeliveryMode.PERSISTENT);
        basicProducer = Mockito.spy(new BasicProducer(brokerProducerConsumer));
        basicProducer.postConstruct();
    }

    @Test
    void testInit() throws JMSException {
        Mockito.verify(producer).setDeliveryMode(DeliveryMode.PERSISTENT);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testSendMessage(boolean withException, @Mock TextMessage txtMsg) throws JMSException {
        Mockito.doReturn(session).when(brokerProducerConsumer).getSession();
        Mockito.doReturn(txtMsg).when(session).createTextMessage(Mockito.anyString());
        if (withException) {
            Mockito.doThrow(new JMSException("error")).when(producer).send(txtMsg);
        } else {
            Mockito.doNothing().when(producer).send(txtMsg);
        }
        Mockito.doThrow(new JMSException("error")).when(producer).close();
        Mockito.doNothing().when(brokerProducerConsumer).closeResources();
        basicProducer.produceMessages();
        Mockito.verify(producer, Mockito.atLeastOnce()).send(txtMsg);
        Mockito.verify(producer, Mockito.atLeastOnce()).close();
    }
}
