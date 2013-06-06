package com.lloyds.brownbag.amqp.trafficlights.consumer.android;

import java.io.UnsupportedEncodingException;

import com.example.test.R;
import com.lloyds.brownbag.amqp.trafficlights.consumer.android.MessageConsumer.OnReceiveMessageHandler;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ScrollView;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class FullscreenActivity extends Activity {
	private MessageConsumer mConsumer;
	private ScrollView scrollView;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	if (android.os.Build.VERSION.SDK_INT > 9) {
    		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    		StrictMode.setThreadPolicy(policy);
    	}
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        
        scrollView = (ScrollView) findViewById(R.id.background);

        mConsumer = new MessageConsumer();
        mConsumer.connectToRabbitMQ();

        mConsumer.setOnReceiveMessageHandler(new OnReceiveMessageHandler(){

			public void onReceiveMessage(byte[] message) {
				String text = "";
				try {
					text = new String(message, "UTF8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				scrollView.setBackgroundColor(Color.parseColor(text));
			}
        });

    }

    @Override
	protected void onResume() {
		super.onPause();
		mConsumer.connectToRabbitMQ();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mConsumer.dispose();
	}
	
	@Override
	protected void onDestroy() {
		super.onPause();
		mConsumer.dispose();
	}
}
