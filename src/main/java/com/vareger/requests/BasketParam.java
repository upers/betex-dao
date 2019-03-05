package com.vareger.requests;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vareger.json.serialization.DateToTimeestampSerializerNumeric;
import com.vareger.json.serialization.DoubleToDateDeserializer;
import com.vareger.json.serialization.IntegerToStatusDeserializer;
import com.vareger.json.serialization.StatusToIntegerSerializer;
import com.vareger.models.BasketStatus;
import com.vareger.validators.ValidCurrencyPair;
import com.vareger.validators.ValidOffsetLimit;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BasketParam {
	
	@ValidCurrencyPair
	private String pairId;
	
	private Long lifeTime;

	private Long waitTime;
	
	public BasketParam() {
		this.limit = 200;
	}
	
	@JsonDeserialize(using = IntegerToStatusDeserializer.class)
	@JsonSerialize(using = StatusToIntegerSerializer.class)
	private BasketStatus state;
	
	@ValidOffsetLimit
	private Integer offset;
	
	@ValidOffsetLimit
	private Integer limit;
	
	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	@JsonSerialize(using = DateToTimeestampSerializerNumeric.class)
	private Date from;
	
	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	@JsonSerialize(using = DateToTimeestampSerializerNumeric.class)
	private Date to;

	public String getPairId() {
		return pairId;
	}

	public void setPairId(String pairId) {
		this.pairId = pairId;
	}

	public Long getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(Long lifeTime) {
		this.lifeTime = lifeTime;
	}

	public Long getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(Long waitTime) {
		this.waitTime = waitTime;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public BasketStatus getState() {
		return state;
	}

	public void setState(BasketStatus state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "BasketParam [pairId=" + pairId + ", lifeTime=" + lifeTime + ", waitTime=" + waitTime + ", state=" + state + ", offset=" + offset + ", limit=" + limit + ", from="
				+ from + ", to=" + to + "]";
	}


}
