package com.ttiwd.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CurrencyServiceExchangeProxy currencyServiceExchangeProxy;
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity) {
		Map<String,String> uriVariables = new HashMap<String,String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversionBean>  entity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/USD/to/INR", 
				CurrencyConversionBean.class,uriVariables);
		CurrencyConversionBean ccb = entity.getBody();
		return new CurrencyConversionBean(ccb.getId(),from,to,
				quantity,quantity.multiply(ccb.getConversionMultiple()),ccb.getConversionMultiple(),ccb.getPort());
	}

	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity) {


		CurrencyConversionBean ccb = currencyServiceExchangeProxy.retrieveExchangeValue(from, to);
		logger.info("ccb -> {}",ccb);

		return new CurrencyConversionBean(ccb.getId(),from,to,
				quantity,quantity.multiply(ccb.getConversionMultiple()),ccb.getConversionMultiple(),ccb.getPort());
	}

}
