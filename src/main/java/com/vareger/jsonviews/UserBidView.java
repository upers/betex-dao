package com.vareger.jsonviews;

import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vareger.json.serialization.DateToTimeestampSerializerNumeric;
import com.vareger.json.serialization.DoubleToDateDeserializer;
import com.vareger.models.Basket;
import com.vareger.models.BidType;
import com.vareger.models.Rate;
import com.vareger.models.UserBid;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserBidView {

	private BasketView basket;

	private String id;

	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	@JsonSerialize(using = DateToTimeestampSerializerNumeric.class)
	private Date timestamp;

	private BidType type;

	private BigInteger value;

	private BigInteger winValue;

	private Double strikeRate;

	private String pairId;

	private Double currentRate;

	private boolean confirmed;

	public UserBidView() {
		basket = new BasketView();
	}

	public UserBidView(UserBid userBid) {
		this.id = userBid.getTxHash();
		this.timestamp = userBid.getBidDate();
		this.type = userBid.getBidType();
		Basket basket = userBid.getBasket();
		this.value = userBid.getAmount();
		this.winValue = userBid.getWinAmount();
		this.confirmed = userBid.isConfirmed();
		if (basket != null) {
			this.basket = new BasketView(basket);
			this.strikeRate = basket.getStrikeRate();
			String currCode = basket.getCurrency().getCode();
			String baseCurrCode = basket.getBaseCurrency().getCode();
			this.pairId = currCode + "_" + baseCurrCode;
		}
	}

	public UserBidView(UserBid userBid, Double currentRate) {
		this.id = userBid.getTxHash();
		this.timestamp = userBid.getBidDate();
		this.type = userBid.getBidType();
		Basket basket = userBid.getBasket();
		this.value = userBid.getAmount();
		this.winValue = userBid.getWinAmount();
		this.confirmed = userBid.isConfirmed();
		this.currentRate = currentRate;
		if (basket != null) {
			this.basket = new BasketView(basket);
			this.strikeRate = basket.getStrikeRate();
			String currCode = basket.getCurrency().getCode();
			String baseCurrCode = basket.getBaseCurrency().getCode();
			this.pairId = currCode + "_" + baseCurrCode;
		}
	}

	public BasketView getBasket() {
		return basket;
	}

	public void setBasket(BasketView basket) {
		this.basket = basket;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public BidType getType() {
		return type;
	}

	public void setType(BidType type) {
		this.type = type;
	}

	public Double getStrikeRate() {
		return strikeRate;
	}

	public void setStrikeRate(Double strikeRate) {
		this.strikeRate = strikeRate;
	}

	public BigInteger getValue() {
		return value;
	}

	public void setValue(BigInteger value) {
		this.value = value;
	}

	public BigInteger getWinValue() {
		return winValue;
	}

	public void setWinValue(BigInteger winValue) {
		this.winValue = winValue;
	}

	public String getPairId() {
		return pairId;
	}

	public void setPairId(String pairId) {
		this.pairId = pairId;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Double getCurrentRate() {
		return currentRate;
	}

	public void setCurrentRate(Double currentRate) {
		this.currentRate = currentRate;
	}

	@Override
	public String toString() {
		return "UserBidView [basket=" + basket + ", id=" + id + ", timestamp=" + timestamp + ", type=" + type + ", value=" + value + ", winValue="
				+ winValue + ", strikeRate=" + strikeRate + ", pairId=" + pairId + ", currentRate=" + currentRate + ", confirmed=" + confirmed + "]";
	}

}
