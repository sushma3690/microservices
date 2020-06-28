package com.ttiwd.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//import com.netflix.hystrix.*;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.ttiwd.microservices.limitsservice.bean.LimitsConfiguration;

@RestController
public class LimitsConfigurationController {
	
	@Autowired
	private Configuration configuration;
	@GetMapping("/limits")
	public LimitsConfiguration retrieveLimitsFromConfiguration() {
		return new LimitsConfiguration(configuration.getMaximum(),configuration.getMinimum());
	}
	
	@GetMapping("/fault-tolerance-example")
	//@HystrixCommand(fallbackMethod="retrieveFallBackConfig")
	public LimitsConfiguration retrieveLimitsFromConfiguration1() {
		throw new RuntimeException("Not Available");
	}
	
	public LimitsConfiguration retrieveFallBackConfig() {
		return new LimitsConfiguration(99,9);
	}

}
