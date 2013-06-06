package com.lloyds.brownbag.amqp.trafficlights.publisher;

import com.rabbitmq.client.Channel;

import java.io.IOException;

final class PublishTask implements Runnable {

    String[] colors = new String[]{"Red", "Yellow", "Green"};

    private final Channel channel;

    public PublishTask(Channel channel) {
        this.channel = channel;
    }

    public void run() {
        int i = -1;
        while (!Thread.interrupted()) {
            i++;
            if(i == colors.length){
                i = 0;
            }
            try {
                String color = colors[i];
                System.out.println(color);

                channel.exchangeDeclare("my_exchange", "fanout");
                channel.queueDeclare("queue", true, false, false, null);
                channel.queueBind("queue", "my_exchange", "key");
                channel.basicPublish("my_exchange", "key", null, color.getBytes());

                Thread.sleep(100);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
