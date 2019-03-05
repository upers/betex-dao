package com.vareger.model.wrappers;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vareger.json.serialization.DateToTimeestampSerializer;
import com.vareger.json.serialization.DoubleToDateDeserializer;
import com.vareger.models.Basket;
import com.vareger.models.BasketStatus;
import com.vareger.models.UserBid;

@JsonInclude(Include.NON_NULL)
public class OrderWrapper {
	private Integer orderId;
	
	private String currency;
	
	private String baseCurrency;
	
	//put, call
	private String type;
	
	@JsonDeserialize(using=DoubleToDateDeserializer.class)
	@JsonSerialize(using=DateToTimeestampSerializer.class)
	private Date startTime;
	
	@JsonDeserialize(using=DoubleToDateDeserializer.class)
	@JsonSerialize(using=DateToTimeestampSerializer.class)
	private Date closeTime;
	
	@JsonDeserialize(using=DoubleToDateDeserializer.class)
	@JsonSerialize(using=DateToTimeestampSerializer.class)
	private Date endTime;
	
	private Double strikeRate;
	
	private Double currentRate;
	
	private Double winAmount;
	
	private BasketStatus status;
	
	private Long timeLeft;
	
	public OrderWrapper() {}
	
	public OrderWrapper(UserBid order) {
		Basket basket = order.getBasket();
		this.orderId = order.getId();
		this.status = basket.getStatus();
		this.type = order.getBidType().getName();
		this.startTime = basket.getOpenTime();
		this.closeTime = basket.getCloseTime();
		this.endTime = basket.getFinishTime();
		this.currency = basket.getCurrency().getCode();
		this.baseCurrency = basket.getBaseCurrency().getCode();
		this.strikeRate = basket.getStrikeRate();
		this.currentRate = basket.getRate();
	}
	
	public Integer getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getStrikeRate() {
		return strikeRate;
	}
	
	public void setStrikeRate(Double strikeRate) {
		this.strikeRate = strikeRate;
	}
	
	public Double getCurrentRate() {
		return currentRate;
	}
	
	public void setCurrentRate(Double currentRate) {
		this.currentRate = currentRate;
	}
	
	public Double getWinAmount() {
		return winAmount;
	}
	
	public void setWinAmount(Double winAmount) {
		this.winAmount = winAmount;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public BasketStatus getStatus() {
		return status;
	}

	public void setStatus(BasketStatus status) {
		this.status = status;
	}
	
	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Long getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(Long timeLeft) {
		this.timeLeft = timeLeft;
	}
	
}
