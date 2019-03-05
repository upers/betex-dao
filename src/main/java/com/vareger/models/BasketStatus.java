package com.vareger.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BasketStatus {
	
	@JsonProperty("OPENED")
	OPENED("OPENED"),
	
	@JsonProperty("CLOSED")
	CLOSED("CLOSED"),
	
	@JsonProperty("FINISHED")
	FINISHED("FINISHED");
	
	private final String name;
	
	BasketStatus(String name) {
		this.name = name();
	}

	public String getName() {
		return name;
	}
	
}
