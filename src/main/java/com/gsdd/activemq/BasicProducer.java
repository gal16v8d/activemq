package com.gsdd.activemq;

import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.TextMessage;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BasicProducer {

  private static final int PACKAGES_TO_SEND = 200;
  private final AbstractBrokerConfig configuration;
  private MessageProducer messageProducer;

  public void postConstruct() {
    try {
      this.configuration.connectToBroker();
      this.messageProducer =
          this.configuration.getSession().createProducer(this.configuration.getDestination());
      messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
    } catch (JMSException e) {
      log.error("Error {}", e.getMessage(), e);
    }
  }

  public void produceMessages() {
    IntStream.rangeClosed(0, PACKAGES_TO_SEND)
        .forEach(val -> sendMessage("package # %d".formatted(val)));
    closeProducer();
    getConfiguration().closeResources();
  }

  private void sendMessage(String text) {
    try {
      TextMessage message = configuration.getSession().createTextMessage(text);
      messageProducer.send(message);
      log.info("Sending '{}'", message);
    } catch (JMSException e) {
      log.error("Error {}", e.getMessage(), e);
    }
  }

  private void closeProducer() {
    if (messageProducer != null) {
      try {
        messageProducer.close();
      } catch (JMSException e) {
        log.error("Error closing producer: {}", e.getMessage(), e);
      }
    }
  }

  public AbstractBrokerConfig getConfiguration() {
    return configuration;
  }
}
