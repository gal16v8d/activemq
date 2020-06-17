package co.com.gsdd.activemq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BrokerPublishSubscribeTest {

    @Spy
    private BrokerPublishSubscribe brokerPublishSubscribe;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDestinationType() {
        Assertions.assertEquals(DestinationType.TOPIC, brokerPublishSubscribe.destinationType());
    }
}
