package co.com.gsdd.activemq;

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
public class BasicConsumerTest {

    private BasicConsumer basicConsumer;
    @Mock
    private BrokerProducerConsumer brokerProducerConsumer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.doNothing().when(brokerProducerConsumer).connectToBroker();
        basicConsumer = Mockito.spy(new BasicConsumer(brokerProducerConsumer, 0));
    }

    @Test
    public void testReceiveMessage(@Mock Session session, @Mock MessageConsumer consumer) throws JMSException {
        Mockito.doReturn(session).when(brokerProducerConsumer).getSession();
        Mockito.doReturn(consumer).when(session).createConsumer(Mockito.any());
        Mockito.doThrow(new JMSException("error")).when(consumer).setMessageListener(Mockito.any());
        basicConsumer.receiveMessage();
        Mockito.verify(consumer).setMessageListener(Mockito.any());
    }

}
