package com.vareger.stock.cex.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class CexLastPrice {
	
	private Double lastPrice;
	
	private String curr1;
	
	private String curr2;
	
	@JsonGetter("lprice")
	public Double getLastPrice() {
		return lastPrice;
	}

	@JsonSetter("lprice")
	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public String getCurr1() {
		return curr1;
	}

	public void setCurr1(String curr1) {
		this.curr1 = curr1;
	}

	public String getCurr2() {
		return curr2;
	}

	public void setCurr2(String curr2) {
		this.curr2 = curr2;
	}
	
}
