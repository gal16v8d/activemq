package com.gsdd.activemq.exception;

import java.io.Serial;

public class ActiveMQEnvException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -3125442528503490796L;

  public ActiveMQEnvException(String msg) {
    super(msg);
  }
}
