package com.vareger.stock.cex.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vareger.json.serialization.DateToTimeestampSerializerNumeric;
import com.vareger.json.serialization.DoubleToDateDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CexBalance {
	
	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	@JsonSerialize(using=DateToTimeestampSerializerNumeric.class)
	private Date timestamp;
	
	private String username;
	
	private CurrencyBalance usd;
	
	private CurrencyBalance eth;
	
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@JsonGetter("USD")
	public CurrencyBalance getUsd() {
		return usd;
	}
	
	@JsonSetter("USD")
	public void setUsd(CurrencyBalance usd) {
		this.usd = usd;
	}
	
	@JsonGetter("ETH")
	public CurrencyBalance getEth() {
		return eth;
	}
	
	@JsonSetter("ETH")
	public void setEth(CurrencyBalance eth) {
		this.eth = eth;
	}

	public class CurrencyBalance {
		private Double available;
		
		public Double getAvailable() {
			return available;
		}

		public void setAvailable(Double available) {
			this.available = available;
		}

		private Double orders;

		public Double getOrders() {
			return orders;
		}

		public void setOrders(Double orders) {
			this.orders = orders;
		}
	}
}
