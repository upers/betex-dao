package com.vareger.jsonviews;

import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vareger.json.serialization.DateToTimeestampSerializerNumeric;
import com.vareger.json.serialization.DoubleToDateDeserializer;
import com.vareger.json.serialization.IntegerToStatusDeserializer;
import com.vareger.json.serialization.StatusToIntegerSerializer;
import com.vareger.models.Basket;
import com.vareger.models.BasketStatus;
import com.vareger.models.BasketWrapper;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_EMPTY)
public class BasketView {
	private String id;

	private String pairId;

	@JsonDeserialize(using = IntegerToStatusDeserializer.class)
	@JsonSerialize(using = StatusToIntegerSerializer.class)
	private BasketStatus state;

	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	@JsonSerialize(using = DateToTimeestampSerializerNumeric.class)
	private Date timestamp;

	private Long lifeTime;

	private Long waitTime;

	private BigInteger value;

	private Integer orders;

	private Double strikeRate;

	private Double resultRate;

	private Bid call;

	private Bid put;

	private Long salt;

	public BasketView() {
	}

	public BasketView(Basket basket) {
		this(new BasketWrapper(basket));
	}

	public BasketView(BasketWrapper basketWraper) {
		BigInteger callBids = (basketWraper.getCallBids() == null) ? BigInteger.valueOf(0) : basketWraper.getCallBids();
		BigInteger putBids = (basketWraper.getPutBids() == null) ? BigInteger.valueOf(0) : basketWraper.getPutBids();
		Integer callAmount = (basketWraper.getCallAmount() == null) ? 0 : basketWraper.getCallAmount();
		Integer putAmount = (basketWraper.getPutAmount() == null) ? 0 : basketWraper.getPutAmount();

		this.id = basketWraper.getTxHash();
		this.state = basketWraper.getStatus();
		this.timestamp = basketWraper.getOpenTime();
		this.salt = basketWraper.getSalt();
		
		if (basketWraper.getOpenSeconds() != null && basketWraper.getLiveSeconds() != null) {
			this.lifeTime = basketWraper.getOpenSeconds();
			this.waitTime = basketWraper.getLiveSeconds() - lifeTime;
		}
		
		String currCode = basketWraper.getCurrencyCode();
		String baseCurrCode = basketWraper.getBaseCurrencyCode();
		if (currCode != null && baseCurrCode != null) {
			this.pairId = currCode + "_" + baseCurrCode;
		}
		
		this.value = callBids.add(putBids);
		this.orders = callAmount + putAmount;
		this.strikeRate = basketWraper.getStrikeRate();
		this.resultRate = basketWraper.getRate();
		
		BigInteger multiplicator = BigInteger.valueOf(1000000l);
		
		this.call = new Bid();
		this.call.setOrders(callAmount);
		double precentCall = (callBids.compareTo(BigInteger.valueOf(0)) == 0) ? 0 : callBids.multiply(multiplicator).divide(value).doubleValue() / multiplicator.doubleValue();
		this.call.setPercent(precentCall);
		this.call.setValue(callBids);

		this.put = new Bid();
		this.put.setOrders(putAmount);
		double precentPut = (putBids.compareTo(BigInteger.valueOf(0)) == 0) ? 0 : putBids.multiply(multiplicator).divide(value).doubleValue() / multiplicator.doubleValue();
		this.put.setPercent(precentPut);
		this.put.setValue(putBids);
	}

	public static class Bid {
		private BigInteger value;
		private Double percent;
		private Integer orders;

		public BigInteger getValue() {
			return value;
		}

		public void setValue(BigInteger value) {
			this.value = value;
		}

		public Integer getOrders() {
			return orders;
		}

		public void setOrders(Integer orders) {
			this.orders = orders;
		}

		@Override
		public String toString() {
			return "Bid [value=" + value + ", precent=" + percent + ", orders=" + orders + "]";
		}

		public Double getPercent() {
			return percent;
		}

		public void setPercent(Double percent) {
			this.percent = percent;
		}

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPairId() {
		return pairId;
	}

	public void setPairId(String pairId) {
		this.pairId = pairId;
	}

	public BasketStatus getState() {
		return state;
	}

	public void setState(BasketStatus state) {
		this.state = state;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Long getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}

	public Long getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}

	public BigInteger getValue() {
		return value;
	}

	public void setValue(BigInteger value) {
		this.value = value;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public Double getStrikeRate() {
		return strikeRate;
	}

	public void setStrikeRate(Double resultRate) {
		this.strikeRate = resultRate;
	}

	public Bid getCall() {
		return call;
	}

	public void setCall(Bid call) {
		this.call = call;
	}

	public Bid getPut() {
		return put;
	}

	public void setPut(Bid put) {
		this.put = put;
	}

	public Double getResultRate() {
		return resultRate;
	}

	public void setResultRate(Double resultRate) {
		this.resultRate = resultRate;
	}

	public Long getSalt() {
		return salt;
	}

	public void setSalt(Long salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "BasketView [id=" + id + ", pairId=" + pairId + ", state=" + state + ", timestamp=" + timestamp + ", lifeTime=" + lifeTime + ", waitTime=" + waitTime + ", value="
				+ value + ", orders=" + orders + ", strikeRate=" + strikeRate + ", resultRate=" + resultRate + ", call=" + call + ", put=" + put + ", salt=" + salt + "]";
	}

}