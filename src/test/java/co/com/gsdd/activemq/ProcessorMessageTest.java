package co.com.gsdd.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProcessorMessageTest {

    @Spy
    private ProcessorMessage processorMessage = new ProcessorMessage(0);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnMessageWithTextMessage(@Mock TextMessage txtMsg) throws JMSException {
        Mockito.doReturn("test").when(txtMsg).getText();
        Mockito.doNothing().when(txtMsg).acknowledge();
        processorMessage.onMessage(txtMsg);
        Mockito.verify(txtMsg).acknowledge();
    }

    @Test
    public void testOnMessageWithMessage(@Mock Message msg) throws JMSException {
        Mockito.doNothing().when(msg).acknowledge();
        processorMessage.onMessage(msg);
        Mockito.verify(msg).acknowledge();
    }

    @Test
    public void testOnMessageWithJMSException(@Mock TextMessage txtMsg) throws JMSException {
        Mockito.doThrow(new JMSException("error")).when(txtMsg).getText();
        processorMessage.onMessage(txtMsg);
        Mockito.verify(txtMsg, Mockito.never()).acknowledge();
    }
}
