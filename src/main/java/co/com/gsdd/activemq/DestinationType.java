package co.com.gsdd.activemq;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum DestinationType {
    QUEUE("QUEUE.SAMPLE"), TOPIC("TOPIC.SAMPLE");

    private final String value;

}
