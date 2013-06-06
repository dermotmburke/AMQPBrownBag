using System;
using System.Collections;
using System.IO;
using System.Text;

using RabbitMQ.Client;
using RabbitMQ.Client.Content;
using RabbitMQ.Client.Events;
using RabbitMQ.Util;

public class Consumer
{
    protected IModel Model;
    protected IConnection Connection;
    protected string QueueName;

    protected bool isConsuming;

    // used to pass messages back to UI for processing
    public delegate void onReceiveMessage(byte[] message);
    public event onReceiveMessage onMessageReceived;

    public Consumer(string uri)
    {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.Uri = uri;
        Connection = connectionFactory.CreateConnection();
        Model = Connection.CreateModel();
        Model.QueueDeclare("dotNetQueue", true, false, false, null);
        Model.QueueBind("dotNetQueue", "trafficExchange", "");
    }

    //internal delegate to run the queue consumer on a seperate thread
    private delegate void ConsumeDelegate();

    public void StartConsuming()
    {
        isConsuming = true;
        ConsumeDelegate c = new ConsumeDelegate(Consume);
        c.BeginInvoke(null, null);
    }

    public void Consume()
    {
        QueueingBasicConsumer consumer = new QueueingBasicConsumer(Model);
        String consumerTag = Model.BasicConsume("dotNetQueue", false, consumer);
        while (isConsuming)
        {
                RabbitMQ.Client.Events.BasicDeliverEventArgs e = (RabbitMQ.Client.Events.BasicDeliverEventArgs)consumer.Queue.Dequeue();
                IBasicProperties props = e.BasicProperties;
                byte[] body = e.Body;
                onMessageReceived(body);
                Model.BasicAck(e.DeliveryTag, false);
        }
    }

    public void Dispose()
    {
        isConsuming = false;
        if (Connection != null)
            Connection.Close();
        if (Model != null)
            Model.Abort();
    }
}