package com.lloyds.brownbag.amqp.trafficlights.consumer.android;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public abstract class BaseConsumer {

	private static final String BROKER_URL = "amqp://xvjpxzlg:IXe4AHnR9vflfNZUS0bMTIbB2_dBEMcq@bunny.cloudamqp.com/xvjpxzlg";
	private static final String EXCHANGE_NAME = "trafficExchange";
	protected static final String QUEUE_NAME = "androidQueue";
	private static final String ROUTING_KEY = "";
	
	public String mExchange;

	protected Channel channel = null;
	protected Connection mConnection;

	protected boolean Running;

	public void dispose() {
		Running = false;

		try {
			if (mConnection != null)
				mConnection.close();
			if (channel != null)
				channel.abort();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean connectToRabbitMQ() {
		if (channel != null && channel.isOpen())
			return true;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setUri(BROKER_URL);

			mConnection = factory.newConnection();
			channel = mConnection.createChannel();
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
			channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}