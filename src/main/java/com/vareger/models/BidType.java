package com.vareger.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BidType {
	@JsonProperty("NON")
	NON("NON"),
	
	@JsonProperty("CALL")
	CALL("CALL"), 
	
	@JsonProperty("PUT")
	PUT("PUT"),
	
	@JsonProperty("NO_CHANGES")
	NO_CHANGES("NO_CHANGES");
	
	private final String name;
	
	BidType(String name) {
		this.name = name();
	}

	public String getName() {
		return name;
	}
	
	
}
