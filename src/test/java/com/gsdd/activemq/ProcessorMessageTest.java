package com.gsdd.activemq;

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
class ProcessorMessageTest {

  @Spy
  private ProcessorMessage processorMessage = new ProcessorMessage(0);

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testOnMessageWithTextMessage(@Mock TextMessage txtMsg) throws JMSException {
    Mockito.doReturn("test").when(txtMsg).getText();
    Mockito.doNothing().when(txtMsg).acknowledge();
    processorMessage.onMessage(txtMsg);
    Mockito.verify(txtMsg).acknowledge();
  }

  @Test
  void testOnMessageWithMessage(@Mock Message msg) throws JMSException {
    Mockito.doNothing().when(msg).acknowledge();
    processorMessage.onMessage(msg);
    Mockito.verify(msg).acknowledge();
  }

  @Test
  void testOnMessageWithJMSException(@Mock TextMessage txtMsg) throws JMSException {
    Mockito.doThrow(new JMSException("error")).when(txtMsg).getText();
    processorMessage.onMessage(txtMsg);
    Mockito.verify(txtMsg, Mockito.never()).acknowledge();
  }
}
