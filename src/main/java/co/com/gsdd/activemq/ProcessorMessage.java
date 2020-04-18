package co.com.gsdd.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ProcessorMessage implements MessageListener {

    private int timeBetweenMessages;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                log.info("Received: {}", text);
            } else {
                log.info("Received: {}", message);
            }

            Thread.sleep(timeBetweenMessages);
            message.acknowledge();
        } catch (JMSException | InterruptedException e) {
            log.error("Error: {}", e.getMessage(), e);
        }

    }

}
