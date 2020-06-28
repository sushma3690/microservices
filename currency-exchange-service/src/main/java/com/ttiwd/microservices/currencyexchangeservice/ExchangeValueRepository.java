package com.ttiwd.microservices.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue,Integer> {
	
	ExchangeValue findByFromAndTo(String from, String to);

}
