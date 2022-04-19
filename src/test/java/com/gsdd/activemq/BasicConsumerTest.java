package com.gsdd.activemq;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.spy;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BasicConsumerTest {

    private BasicConsumer basicConsumer;
    @Mock private BrokerProducerConsumer brokerProducerConsumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        willDoNothing().given(brokerProducerConsumer).connectToBroker();
        basicConsumer = spy(new BasicConsumer(brokerProducerConsumer, 0));
        basicConsumer.postConstruct();
    }

    @Test
    void testReceiveMessage(@Mock Session session, @Mock MessageConsumer consumer)
            throws JMSException {
        willReturn(session).given(brokerProducerConsumer).getSession();
        willReturn(consumer).given(session).createConsumer(Mockito.any());
        willThrow(new JMSException("error")).given(consumer).setMessageListener(Mockito.any());
        basicConsumer.receiveMessage();
        then(consumer).should().setMessageListener(Mockito.any());
    }
}
