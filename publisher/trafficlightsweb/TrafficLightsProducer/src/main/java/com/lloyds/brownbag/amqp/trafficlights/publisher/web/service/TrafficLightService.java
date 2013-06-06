package com.lloyds.brownbag.amqp.trafficlights.publisher.web.service;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TrafficLightService {

    @Resource
    private Worker worker;

    private long servicePollTime = 1000L;

    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public synchronized void schedule() {
        if(threadPoolTaskScheduler != null){
            return;
        }
        threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        threadPoolTaskScheduler.scheduleWithFixedDelay(worker, servicePollTime);
    }

    public synchronized void stop() {
        if(threadPoolTaskScheduler == null){
            return;
        }
        threadPoolTaskScheduler.shutdown();
        threadPoolTaskScheduler = null;
    }

    public synchronized void setDelay(Long delay) {
        this.servicePollTime = delay;
        stop();
    }
}
