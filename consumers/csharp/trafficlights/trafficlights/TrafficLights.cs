using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace trafficlights
{

    public partial class TrafficLights : Form
    {

        public string HOST_NAME = "amqp://xvjpxzlg:IXe4AHnR9vflfNZUS0bMTIbB2_dBEMcq@bunny.cloudamqp.com/xvjpxzlg";

        private Consumer consumer;

        public TrafficLights()
        {
            InitializeComponent();
            //create the consumer
            consumer = new Consumer(HOST_NAME);

            //listen for message events
            consumer.onMessageReceived += handleMessage;

            //start consuming
            consumer.StartConsuming();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        //Callback for message receive
        public void handleMessage(byte[] message)
        {
            string result = System.Text.Encoding.UTF8.GetString(message);
            Color bgcolor = System.Drawing.ColorTranslator.FromHtml(result);
            this.BackColor = bgcolor;
        }
    }
}
