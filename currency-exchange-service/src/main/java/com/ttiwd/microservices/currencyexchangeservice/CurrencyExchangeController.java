package com.ttiwd.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExchangeValueRepository exchangeValueRepository;
	@Autowired
	private Environment environment;
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		//ExchangeValue ex = new ExchangeValue(1000,from,to,BigDecimal.valueOf(65));
		ExchangeValue ex = exchangeValueRepository.findByFromAndTo(from, to);
		logger.info("cex -> {}",ex);
		ex.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return ex;
	}

}
