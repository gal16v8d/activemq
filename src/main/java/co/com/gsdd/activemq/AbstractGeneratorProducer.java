package co.com.gsdd.activemq;

public abstract class AbstractGeneratorProducer {

    private static final int PACKAGES_TO_SEND = 200;

    public void produceMessages(BasicProducer producer) {
        for (int i = 0; i < PACKAGES_TO_SEND; i++) {
            producer.sendMessage("Sending package #: " + i);
        }
        producer.closeProducer();
        producer.getConfiguration().closeResources();
    }
}
