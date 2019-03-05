package com.vareger.models;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vareger.json.serialization.DateToTimeestampSerializer;
import com.vareger.json.serialization.DoubleToDateDeserializer;

@Entity
@Table(name="ticker")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class Ticker {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private BigInteger id;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="murket_id")
	@NotNull
	private Murket murket;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="currency_id")
	@NotNull
	private Currency currency;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="base_currency_id")
	@NotNull
	private Currency baseCurrency;
	
	@Column(name="timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using=DoubleToDateDeserializer.class)
	@JsonSerialize(using=DateToTimeestampSerializer.class)
	@NotNull
	private Date date;
	
	@Column(name="last_price")
	@NotNull
	@DecimalMin("0.0")
	private Double lastPrice;
	
	@NotNull
	@DecimalMin("0.0")
	private Double bid;
	
	@NotNull
	@DecimalMin("0.0")
	private Double ask;
	
	@NotNull
	@DecimalMin("0.0")
	private Double volume;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Murket getMurket() {
		return murket;
	}

	public void setMurket(Murket murket) {
		this.murket = murket;
	}
	
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public Currency getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(Currency baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	
	@JsonGetter("timestamp")
	public Date getDate() {
		return date;
	}
	
	@JsonSetter("timestamp")
	public void setDate(Date date) {
		this.date = date;
	}
	
	@JsonSetter("updated")
	@JsonDeserialize(using=DoubleToDateDeserializer.class)
	public void setUpdatedDate(Date date) {
		this.date = date;
	}
	
	@JsonSetter("closeTime")
	@JsonDeserialize(using=DoubleToDateDeserializer.class)
	public void setCloseTime(Date date) {
		this.date = date;
	}
	
	@JsonGetter("last")
	public Double getLastPrice() {
		return lastPrice;
	}
	
	@JsonSetter("last_price")
	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}
	
	@JsonSetter("lastPrice")
	public void setLast_Price(Double lastPrice) {
		this.lastPrice = lastPrice;
	}
	
	@JsonSetter("last")
	public void setLast(Double lastPrice) {
		this.lastPrice = lastPrice;
	}

	@JsonGetter("bid")
	public Double getBid() {
		return bid;
	}
	
	@JsonSetter("bid")
	public void setBid(Double bid) {
		this.bid = bid;
	}
	
	@JsonSetter("bidPrice")
	public void setBidPrice(Double bid) {
		this.bid = bid;
	}
	
	@JsonSetter("sell")
	public void setSell(Double bid) {
		this.bid = bid;
	}
	
	@JsonGetter("ask")
	public Double getAsk() {
		return ask;
	}
	
	@JsonSetter("ask")
	public void setAsk(Double ask) {
		this.ask = ask;
	}
	
	@JsonSetter("askPrice")
	public void setAskPrice(Double ask) {
		this.ask = ask;
	}
	
	@JsonSetter("buy")
	public void setBuy(Double ask) {
		this.ask = ask;
	}
	
	@JsonGetter("volume")
	public Double getVolume() {
		return volume;
	}
	
	@JsonSetter("volume")
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	
	@JsonSetter("vol_cur")
	public void setVolCur(Double volume) {
		this.volume = volume;
	}
	
	@Override
	public String toString() {
		return "Ticker [id=" + id + ", murket=" + murket.getName() + ", currency=" + currency.getCode() + ", baseCurrency=" + baseCurrency.getCode()
				+ ", date=" + date + ", lastPrice=" + lastPrice + "]";
	}
}
