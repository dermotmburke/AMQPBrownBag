package com.lloyds.brownbag.amqp.trafficlights.publisher.web.controller;

import com.lloyds.brownbag.amqp.trafficlights.publisher.web.service.TrafficLightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class TrafficLightsController {

    @Resource
    private TrafficLightService trafficLightService;

    @RequestMapping("home")
    public String home(Model m) {
        return "home";
    }

	@RequestMapping("start")
	public String start(Model m) {
        trafficLightService.schedule();
		return "home";
	}

    @RequestMapping("stop")
    public String stop(Model m) {
        trafficLightService.stop();
        return "home";
    }

    @RequestMapping("setDelay")
    public String setDelay(@ModelAttribute("delay") Long delay) {
        trafficLightService.setDelay(delay);
        return "home";
    }
}
