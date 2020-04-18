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

    private static final String BROKER_SERVER = "tcp://192.168.99.100:61616?jms.prefetchPolicy.queuePrefetch=1";

    private ActiveMQConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;

    public AbstractBrokerConfig() {
        try {
            connectionFactory = new ActiveMQConnectionFactory(BROKER_SERVER);
            // Create a Connection
            connection = connectionFactory.createConnection();
            connection.start();
            // Create a Session
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            defineDestination(destinationType());
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage(), e);
        }
    }

    public abstract DestinationType destinationType();

    // Create the destination (Topic or Queue)
    public void defineDestination(DestinationType destinationType) throws JMSException {
        switch (destinationType) {
        case QUEUE:
            destination = session.createQueue(DestinationType.QUEUE.getValue());
            break;
        case TOPIC:
            destination = session.createTopic(DestinationType.TOPIC.getValue());
            break;
        default:
            throw new IllegalArgumentException("DestinationType is not valid.");
        }
    }

    public void closeResources() {
        if (session != null) {
            try {
                session.close();
            } catch (JMSException e) {
                log.error("Error closing session: {}", e.getMessage(), e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                log.error("Error closing connection: {}", e.getMessage(), e);
            }
        }
    }

}
