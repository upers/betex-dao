package com.vareger.stock.cex.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CexCryptoAddress {
	
	private String address;
	
	@JsonGetter("data")
	public String getAddress() {
		return address;
	}

	@JsonSetter("data")
	public void setAddress(String address) {
		this.address = address;
	}
}
