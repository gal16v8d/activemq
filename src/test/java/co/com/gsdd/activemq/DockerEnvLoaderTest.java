package co.com.gsdd.activemq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

public class DockerEnvLoaderTest {

    @Test
    @EnabledIfEnvironmentVariable(named = "DOCKER_HOST", matches = "tcp://192.168.99.113:2376")
    public void testGetActiveMQBrokerServer() {
        Assertions.assertEquals("tcp://192.168.99.113", DockerEnvLoader.getActiveMQBrokerServer());
    }
}
