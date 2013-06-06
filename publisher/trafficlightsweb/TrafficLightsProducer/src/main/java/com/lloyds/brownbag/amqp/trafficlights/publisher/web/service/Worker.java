package com.lloyds.brownbag.amqp.trafficlights.publisher.web.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Worker implements Runnable {

    private static final String[] COLORS = new String[]{"Red", "Yellow", "Green"};

    private AtomicInteger i = new AtomicInteger(0);

    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    public void run() {
        if(i.get() == COLORS.length){
            i.set(0);
        }
        amqpTemplate.convertAndSend(COLORS[i.get()]);
        i.incrementAndGet();
    }
}
