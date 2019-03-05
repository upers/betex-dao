package com.vareger.models;

import com.fasterxml.jackson.databind.JsonNode;

public class Contract {
	private String address;
	
	private JsonNode abi;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public JsonNode getAbi() {
		return abi;
	}

	public void setAbi(JsonNode abi) {
		this.abi = abi;
	}
}
