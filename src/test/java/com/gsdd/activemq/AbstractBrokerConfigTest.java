package com.gsdd.activemq;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.never;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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

  @Test
  void testConnectToBrokerCanNotStartConnection(@Mock ActiveMQConnectionFactory connectionFactory,
      @Mock Connection connection, @Mock Session session) throws JMSException {
    willReturn(connectionFactory).given(config).initConnectionFactory();
    willReturn(connection).given(connectionFactory).createConnection();
    willThrow(new RuntimeException("Error")).given(connection).start();
    config.connectToBroker();
    then(session).should(never()).createQueue(any());
  }

  @Test
  void testConnectToBroker(@Mock ActiveMQConnectionFactory connectionFactory,
      @Mock Connection connection, @Mock Session session) throws JMSException {
    mockBrokerConfigs(connectionFactory, connection, session);
    willReturn(null).given(session).createQueue(any());
    config.connectToBroker();
    then(session).should().createQueue(any());
  }

  @Test
  void testConnectToBrokerTopic(@Mock ActiveMQConnectionFactory connectionFactory,
      @Mock Connection connection, @Mock Session session) throws JMSException {
    willReturn(DestinationType.TOPIC).given(config).destinationType();
    mockBrokerConfigs(connectionFactory, connection, session);
    willReturn(null).given(session).createTopic(any());
    config.connectToBroker();
    then(session).should().createTopic(any());
  }

  @Test
  void testConnectToBrokerBadDestination(@Mock ActiveMQConnectionFactory connectionFactory,
      @Mock Connection connection, @Mock Session session) throws JMSException {
    willReturn(null).given(config).destinationType();
    mockBrokerConfigs(connectionFactory, connection, session);
    config.connectToBroker();
    then(session).should(never()).createTopic(any());
    then(session).should(never()).createQueue(any());
  }

  private void mockBrokerConfigs(ActiveMQConnectionFactory connectionFactory, Connection connection,
      Session session) throws JMSException {
    willReturn(connectionFactory).given(config).initConnectionFactory();
    willReturn(connection).given(connectionFactory).createConnection();
    willDoNothing().given(connection).start();
    willReturn(session).given(connection).createSession(false, Session.CLIENT_ACKNOWLEDGE);
  }

  @Test
  void testCloseResources(@Mock Session session, @Mock Connection connection) throws JMSException {
    willReturn(session).given(config).getSession();
    willReturn(connection).given(config).getConnection();
    willDoNothing().given(session).close();
    willDoNothing().given(connection).close();
    config.closeResources();
    then(session).should().close();
    then(connection).should().close();
  }

  @Test
  void testCloseResourcesWithErrors(@Mock Session session, @Mock Connection connection)
      throws JMSException {
    willReturn(session).given(config).getSession();
    willReturn(connection).given(config).getConnection();
    willThrow(new JMSException("error")).given(session).close();
    willThrow(new JMSException("error")).given(connection).close();
    config.closeResources();
    then(session).should().close();
    then(connection).should().close();
  }

  @Test
  void testCloseResourcesNoSession(@Mock Connection connection) throws JMSException {
    willReturn(connection).given(config).getConnection();
    willDoNothing().given(connection).close();
    config.closeResources();
    then(connection).should().close();
  }

  @Test
  void testCloseResourcesNoConnection(@Mock Session session) throws JMSException {
    willReturn(session).given(config).getSession();
    willDoNothing().given(session).close();
    config.closeResources();
    then(session).should().close();
  }
}
