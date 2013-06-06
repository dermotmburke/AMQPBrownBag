package com.lloyds.brownbag.amqp.trafficlights.publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author Rob Harrop
 */
public class Publisher {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUri("amqp://xvjpxzlg:IXe4AHnR9vflfNZUS0bMTIbB2_dBEMcq@bunny.cloudamqp.com/xvjpxzlg");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        Thread thread = new Thread(new PublishTask(channel));
        thread.start();
        System.in.read();
        thread.interrupt();

        channel.close();
        connection.close();
    }

}
