# activemq

Basic sample of ActiveMQ usage.
Point to Point (Queue)
Publish - Subscribe (Topic)

For all the samples you will need:

* Java 8
* Maven
* Docker

For get started with the docker image execute:

docker pull rmohr/activemq
docker run -p 61616:61616 -p 8161:8161 rmohr/activemq

After run activemq docker image, you can run the sample
For ** Publish - Subscribe **
Run ExecutePublishSubscribe.java

For **Point to Point **
Run ExecutePTP.java