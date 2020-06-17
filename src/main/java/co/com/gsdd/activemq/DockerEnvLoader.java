package co.com.gsdd.activemq;

import java.util.Optional;

import co.com.gsdd.activemq.exception.ActiveMQEnvException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DockerEnvLoader {

    public static String getActiveMQBrokerServer() {
        String dockerHost = System.getenv("DOCKER_HOST");
        return Optional.ofNullable(dockerHost).map(host -> host.substring(0, host.lastIndexOf(':')))
                .orElseThrow(() -> new ActiveMQEnvException("No docker env configured for run activemq"));
    }

}
