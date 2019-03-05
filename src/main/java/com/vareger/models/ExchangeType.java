package com.vareger.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ExchangeType {
	@JsonProperty("BUY")
	BUY("BUY"),
	
	@JsonProperty("SELL")
	SELL("SELL");
	
	private final String name;
	
	ExchangeType(String name) {
		this.name = name();
	}

	public String getName() {
		return name;
	}
	
	
}
