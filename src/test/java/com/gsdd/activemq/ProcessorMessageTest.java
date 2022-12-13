package com.gsdd.activemq;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.never;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
    willReturn("test").given(txtMsg).getText();
    willDoNothing().given(txtMsg).acknowledge();
    processorMessage.onMessage(txtMsg);
    then(txtMsg).should().acknowledge();
  }

  @Test
  void testOnMessageWithMessage(@Mock Message msg) throws JMSException {
    willDoNothing().given(msg).acknowledge();
    processorMessage.onMessage(msg);
    then(msg).should().acknowledge();
  }

  @Test
  void testOnMessageWithJMSException(@Mock TextMessage txtMsg) throws JMSException {
    willThrow(new JMSException("error")).given(txtMsg).getText();
    processorMessage.onMessage(txtMsg);
    then(txtMsg).should(never()).acknowledge();
  }
}
