package co.com.gsdd.activemq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AbstractBrokerConfigTest {

    public static class BrokerConfig extends AbstractBrokerConfig {
        @Override
        public DestinationType destinationType() {
            return DestinationType.QUEUE;
        }
    }

    @Spy
    private BrokerConfig config;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConnectToBroker(@Mock ActiveMQConnectionFactory connectionFactory, @Mock Connection connection,
            @Mock Session session) throws JMSException {
        mockBrokerConfigs(connectionFactory, connection, session);
        Mockito.doReturn(null).when(session).createQueue(Mockito.any());
        config.connectToBroker();
        Mockito.verify(session).createQueue(Mockito.any());
    }

    @Test
    void testConnectToBrokerTopic(@Mock ActiveMQConnectionFactory connectionFactory, @Mock Connection connection,
            @Mock Session session) throws JMSException {
        Mockito.doReturn(DestinationType.TOPIC).when(config).destinationType();
        mockBrokerConfigs(connectionFactory, connection, session);
        Mockito.doReturn(null).when(session).createTopic(Mockito.any());
        config.connectToBroker();
        Mockito.verify(session).createTopic(Mockito.any());
    }

    @Test
    void testConnectToBrokerBadDestination(@Mock ActiveMQConnectionFactory connectionFactory,
            @Mock Connection connection, @Mock Session session) throws JMSException {
        Mockito.doReturn(null).when(config).destinationType();
        mockBrokerConfigs(connectionFactory, connection, session);
        config.connectToBroker();
        Mockito.verify(session, Mockito.never()).createTopic(Mockito.any());
        Mockito.verify(session, Mockito.never()).createQueue(Mockito.any());
    }

    private void mockBrokerConfigs(ActiveMQConnectionFactory connectionFactory, Connection connection, Session session)
            throws JMSException {
        Mockito.doReturn(connectionFactory).when(config).initConnectionFactory();
        Mockito.doReturn(connection).when(connectionFactory).createConnection();
        Mockito.doNothing().when(connection).start();
        Mockito.doReturn(session).when(connection).createSession(false, Session.CLIENT_ACKNOWLEDGE);
    }

    @Test
    void testCloseResources(@Mock Session session, @Mock Connection connection) throws JMSException {
        Mockito.doReturn(session).when(config).getSession();
        Mockito.doReturn(connection).when(config).getConnection();
        Mockito.doNothing().when(session).close();
        Mockito.doNothing().when(connection).close();
        config.closeResources();
        Mockito.verify(session).close();
        Mockito.verify(connection).close();
    }

    @Test
    void testCloseResourcesWithErrors(@Mock Session session, @Mock Connection connection) throws JMSException {
        Mockito.doReturn(session).when(config).getSession();
        Mockito.doReturn(connection).when(config).getConnection();
        Mockito.doThrow(new JMSException("error")).when(session).close();
        Mockito.doThrow(new JMSException("error")).when(connection).close();
        config.closeResources();
        Mockito.verify(session).close();
        Mockito.verify(connection).close();
    }

    @Test
    void testCloseResourcesNoSession(@Mock Connection connection) throws JMSException {
        Mockito.doReturn(connection).when(config).getConnection();
        Mockito.doNothing().when(connection).close();
        config.closeResources();
        Mockito.verify(connection).close();
    }

    @Test
    void testCloseResourcesNoConnection(@Mock Session session) throws JMSException {
        Mockito.doReturn(session).when(config).getSession();
        Mockito.doNothing().when(session).close();
        config.closeResources();
        Mockito.verify(session).close();
    }
}
