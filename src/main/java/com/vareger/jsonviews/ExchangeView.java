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
import com.vareger.models.Exchange;
import com.vareger.models.ExchangeType;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_EMPTY)
public class ExchangeView {

	private String id;

	private String address;

	private String currency;

	private ExchangeType type;

	private BigInteger amount;
	
	private boolean confirmed;
	
	private Float progress;

	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	@JsonSerialize(using = DateToTimeestampSerializerNumeric.class)
	private Date time;
	
	/**
	 * It's course for which we bought tokens
	 */
	private Double rate;

	public ExchangeView() {
	}

	public ExchangeView(Exchange exchange) {
		if (exchange == null)
			return;

		this.currency = (exchange.getCurrency() == null) ? "eth" : exchange.getCurrency().getCode();
		this.id = exchange.getUserTxHash();
		this.address = exchange.getUserAddress();
		this.type = exchange.getType();
		this.amount = exchange.getAmount();
		this.rate = exchange.getRate();
		this.time = exchange.getTime();
		this.confirmed = exchange.isConfirmed();
		BigInteger confirmations = exchange.getConfirmations();
		BigInteger expConfirmations = exchange.getExpectedConfirmations();
		if (confirmations != null && expConfirmations != null) {
			progress = confirmations.floatValue() / expConfirmations.floatValue();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String txHash) {
		this.id = txHash;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public ExchangeType getType() {
		return type;
	}

	public void setType(ExchangeType type) {
		this.type = type;
	}

	public BigInteger getAmount() {
		return amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Float getProgress() {
		return progress;
	}

	public void setProgress(Float progress) {
		this.progress = progress;
	}

	@Override
	public String toString() {
		return "ExchangeView [id=" + id + ", address=" + address + ", currency=" + currency + ", type=" + type + ", amount=" + amount + ", confirmed="
				+ confirmed + ", progress=" + progress + ", time=" + time + ", rate=" + rate + "]";
	}

}
