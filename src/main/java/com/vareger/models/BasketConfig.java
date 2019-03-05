package com.vareger.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class BasketConfig {
	private String currency;
	
	private String baseCurrency;
	
	private Integer liveTime;
	
	private Integer openTime;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public Integer getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(Integer liveTime) {
		this.liveTime = liveTime;
	}
	
	public Integer getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Integer openTime) {
		this.openTime = openTime;
	}
	
	@Override
	public String toString() {
		return "BasketConfig [currency=" + currency + ", baseCurrency=" + baseCurrency + ", liveTime=" + liveTime + ", openTime=" + openTime
				+ "]";
	}

	@Override
	public BasketConfig clone() {
		BasketConfig newBasketConfig = new BasketConfig();
		newBasketConfig.setLiveTime(this.liveTime);
		newBasketConfig.setOpenTime(this.openTime);
		newBasketConfig.setCurrency(this.currency);
		newBasketConfig.setBaseCurrency(this.baseCurrency);
		
		return newBasketConfig;
	}

}
