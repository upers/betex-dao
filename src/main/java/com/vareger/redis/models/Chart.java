package com.vareger.redis.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.vareger.models.Rate;

@RedisHash("chart")
@JsonPropertyOrder({ "currencyCode", "baseCurrencyCode", "rates" })
public class Chart {
	
	@Id
	@JsonIgnore
	private String key;
	
	@Indexed
	private String currencyCode;
	
	@Indexed
	private String baseCurrencyCode;
	
	@Indexed
	private List<Rate> rates;
	
	@TimeToLive
	public Long timeToLive() {
		return -1l;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@JsonGetter("baseCurrency")
	public String getBaseCurrencyCode() {
		return baseCurrencyCode;
	}
	
	@JsonSetter("baseCurrency")
	public void setBaseCurrencyCode(String baseCurrencyCode) {
		this.baseCurrencyCode = baseCurrencyCode;
	}
	
	@JsonGetter("currency")
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	@JsonSetter("currency")
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public List<Rate> getRates() {
		return rates;
	}

	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}
	

}
