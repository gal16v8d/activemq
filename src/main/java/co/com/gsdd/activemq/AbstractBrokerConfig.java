package co.com.gsdd.activemq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public abstract class AbstractBrokerConfig {

    private static final String BROKER_SERVER_FMT = "%s:61616?jms.prefetchPolicy.queuePrefetch=1";

    private ActiveMQConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;

    public abstract DestinationType destinationType();

    public void connectToBroker() {
        try {
            connectionFactory = initConnectionFactory();
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            defineDestination(destinationType());
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage(), e);
        }
    }

    protected ActiveMQConnectionFactory initConnectionFactory() {
        return new ActiveMQConnectionFactory(
                String.format(BROKER_SERVER_FMT, DockerEnvLoader.getActiveMQBrokerServer()));
    }

    // Create the destination (Topic or Queue)
    private void defineDestination(DestinationType destinationType) throws JMSException {
        if (DestinationType.QUEUE.equals(destinationType)) {
            destination = session.createQueue(DestinationType.QUEUE.getValue());
        } else if (DestinationType.TOPIC.equals(destinationType)) {
            destination = session.createTopic(DestinationType.TOPIC.getValue());
        }
    }

    public void closeResources() {
        if (getSession() != null) {
            try {
                getSession().close();
            } catch (JMSException e) {
                log.error("Error closing session: {}", e.getMessage(), e);
            }
        }
        if (getConnection() != null) {
            try {
                getConnection().close();
            } catch (JMSException e) {
                log.error("Error closing connection: {}", e.getMessage(), e);
            }
        }
    }

}
