package com.vareger.models;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class BasketWrapper {
	
	@JsonIgnore
	private Basket basket;
	
	private String currencyCode;
	
	private String baseCurrencyCode;

	public BasketWrapper() {
		basket = new Basket();
	}
	
	public BasketWrapper(Basket basket) {
		if (basket == null)
			this.basket = new Basket();
		else {
			this.basket = basket;
			this.currencyCode = basket.getCurrency().getCode();
			this.baseCurrencyCode = basket.getBaseCurrency().getCode();
		}
	}
	
	public BasketWrapper(Basket basket, String currCode, String baseCurrCode) {
		if (basket == null)
			this.basket = new Basket();
		else 
			this.basket = basket;
		
		this.currencyCode = currCode;
		this.baseCurrencyCode = baseCurrCode;
	}
	
	public Integer getId() {
		return basket.getId();
	}

	public void setId(Integer id) {
		basket.setId(id);
	}

	public Double getStrikeRate() {
		return basket.getStrikeRate();
	}

	public void setStrikeRate(Double strikeRate) {
		basket.setStrikeRate(strikeRate);
	}
	
	public Long getLiveSeconds() {
		return basket.getLiveSeconds();
	}

	public void setLiveSeconds(Long liveSeconds) {
		this.basket.setLiveSeconds(liveSeconds);
	}

	public Long getOpenSeconds() {
		return basket.getOpenSeconds();
	}

	public void setOpenSeconds(Long openSeconds) {
		this.basket.setOpenSeconds(openSeconds);
	}
	
	@JsonIgnore
	public Currency getCurrency() {
		return basket.getCurrency();
	}

	public void setCurrency(Currency currency) {
		currencyCode = currency.getCode();
		basket.setCurrency(currency);
	}
	
	@JsonIgnore
	public Currency getBaseCurrency() {
		return basket.getBaseCurrency();
	}

	public void setBaseCurrency(Currency baseCurrency) {
		baseCurrencyCode = baseCurrency.getCode();
		basket.setBaseCurrency(baseCurrency);
	}

	public Date getCloseTime() {
		return basket.getCloseTime();
	}

	public void setCloseTime(Date closeTime) {
		basket.setCloseTime(closeTime);
	}

	public Double getRate() {
		return basket.getRate();
	}

	public void setRate(Double rate) {
		basket.setRate(rate);
	}

	public BasketStatus getStatus() {
		return basket.getStatus();
	}

	public void setStatus(BasketStatus status) {
		basket.setStatus(status);
	}

	@JsonIgnore
	public List<UserBid> getOrders() {
		return basket.getUserBids();
	}
	
	@JsonIgnore
	public void setOrders(List<UserBid> orders) {
		basket.setUserBids(orders);
	}

	public Date getOpenTime() {
		return basket.getOpenTime();
	}

	public void setOpenTime(Date openTime) {
		basket.setOpenTime(openTime);
	}

	public Date getFinishTime() {
		return basket.getFinishTime();
	}

	public void setFinishTime(Date finishTime) {
		basket.setFinishTime(finishTime);
	}

	public Basket getBasket() {
		return basket;
	}
	
	@JsonGetter("currency")
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	@JsonSetter("currency")
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	@JsonGetter("baseCurrency")
	public String getBaseCurrencyCode() {
		return baseCurrencyCode;
	}
	
	@JsonSetter("baseCurrency")
	public void setBaseCurrencyCode(String baseCurrencyCode) {
		this.baseCurrencyCode = baseCurrencyCode;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}
	
	public BigInteger getCallBids() {
		return this.basket.getCallBids();
	}

	public void setCallBids(BigInteger callBids) {
		this.basket.setCallBids(callBids);
	}

	public BigInteger getPutBids() {
		return basket.getPutBids();
	}

	public void setPutBids(BigInteger putBids) {
		this.basket.setPutBids(putBids);
	}
	

	public Long getSalt() {
		return basket.getSalt();
	}
	
	public void setSalt(long salt) {
		basket.setSalt(salt);
	}
	public Integer getCallAmount() {
		return basket.getCallAmount();
	}

	public void setCallAmount(Integer callAmount) {
		this.basket.setCallAmount(callAmount);
	}

	public Integer getPutAmount() {
		return basket.getPutAmount();
	}

	public void setPutAmount(Integer putAmount) {
		this.basket.setPutAmount(putAmount);
	}

	public String getTxHash() {
		return basket.getTxHash();
	}

	public void setTxHash(String transactionId) {
		this.basket.setTxHash(transactionId);
	}
	
	public BigInteger getBlockNum() {
		return basket.getBlockNum();
	}

	public void setBlockNum(BigInteger blockNum) {
		this.basket.setBlockNum(blockNum);
	}

	public Integer getTxIndex() {
		return basket.getTxIndex();
	}

	public void setTxIndex(Integer txIndex) {
		this.basket.setTxIndex(txIndex);
	}
/*	
	public BigInteger getTotalSupply() {
		return basket.getTotalSupply();
	}

	public void setTotalSupply(BigInteger totalSupply) {
		this.basket.setTotalSupply(totalSupply);
	}

	public BigInteger getBotTotalSupply() {
		return basket.getTotalSupply();
	}

	public void setBotTotalSupply(BigInteger botTotalSupply) {
		this.basket.setBotTotalSupply(botTotalSupply);
	}
	public BigInteger getRealSupply() {
		return basket.getRealSupply();
	}

	public void setRealSupply(BigInteger realSupply) {
		this.basket.setRealSupply(realSupply);
	}
*/	

	public void merge(BasketWrapper basketWrapper) {
		Basket basket = basketWrapper.getBasket();
		
		this.basket.merge(basket);
		if (basket != null) {
			this.currencyCode = (basket.getCurrency() != null) ? basket.getCurrency().getCode() : basketWrapper.getCurrencyCode();
			this.baseCurrencyCode = (basket.getBaseCurrency() != null) ? basket.getBaseCurrency().getCode() : basketWrapper.getBaseCurrencyCode();
		}
	}
	
	@Override
	public String toString() {
		String result = "";
		if (basket != null)
			result += basket.toString();
		
		result += " ]";
		
		return result; 
	}

}
