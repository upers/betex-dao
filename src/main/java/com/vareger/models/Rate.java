package com.vareger.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vareger.json.serialization.DateToTimeestampSerializer;
import com.vareger.json.serialization.DoubleToDateDeserializer;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Rate implements Comparable<Rate> {
	
	@Column(name="timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using=DoubleToDateDeserializer.class)
	@JsonSerialize(using=DateToTimeestampSerializer.class)
	private Date date;
	
	@Transient
	@JsonProperty(value ="id")
	private String id;

	@Transient
	@JsonProperty(value ="name")
	private String name;
	
	@Id
	@Column(name="price")
	private Double price;
	
	@Transient
	@JsonProperty(value ="vector")
	private Integer vector;
	
	@Transient
	@JsonProperty(value ="currencyCode",required = true)
	private String currencyCode;
	
	@Transient
	@JsonProperty(value ="baseCurrencyCode",required = true)
	private String baseCurrencyCode;
	
	@Transient
	@JsonProperty(value ="format")
	private String format;
	
	@Transient
	@JsonIgnore
	private Rate penultimateRate;
	
	public Rate() {}
	@JsonGetter("timestamp")
	public Date getDate() {
		return date;
	}
	
	@JsonSetter("timestamp")
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Rate getPenultimateRate() {
		return penultimateRate;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public String getBaseCurrencyCode() {
		return baseCurrencyCode;
	}
	
	public void setBaseCurrencyCode(String baseCurrencyCode) {
		this.baseCurrencyCode = baseCurrencyCode;
	}
	
	public String getFormat() {
		return format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getVector() {
		return vector;
	}
	
	public void setVector(Integer vector) {
		this.vector = vector;
	}
	
	
	public void setPenultimateRate(Rate penultimateRate) {
		this.penultimateRate = penultimateRate;
		
		if (penultimateRate == null || penultimateRate.getPrice() == null || price == null)
			return;
		
		vector = price.compareTo(penultimateRate.getPrice());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Rate))
			return false;
		
		Rate in = (Rate) obj;
		String inCurrCode = in.getCurrencyCode();
		String inBaseCurrCode = in.getBaseCurrencyCode();
		Date inDate = in.getDate();
		Double inPrice = in.getPrice();
		
		if (currencyCode != null) {
			if (!currencyCode.equals(inCurrCode))
				return false;
		} else {
			if (inCurrCode != null)
				return false;
		}
		
		if (baseCurrencyCode != null) {
			if (!baseCurrencyCode.equals(inBaseCurrCode))
				return false;
		} else {
			if (inBaseCurrCode != null)
				return false;
		}
		
		if (date != null) {
			if (inDate == null)
				return false;
			
			long inTime = inDate.getTime() / 1000l;
			long time = date.getTime() / 1000l;
			if (inTime != time)
				return false;
		} else {
			if (inDate != null)
				return false;
		}
		
		if (price != null) {
			if (!price.equals(inPrice))
				return false;
		} else {
			if (inPrice != null)
				return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		if (currencyCode != null)
			hash += currencyCode.hashCode() ;
		if (baseCurrencyCode != null)
			hash += baseCurrencyCode.hashCode();
		if (date != null)
			hash += date.hashCode();
		if (price != null)
			hash += price.hashCode();
		
		return hash;
	}
	
	@Override
	public int compareTo(Rate o) {
		Long time = date.getTime();
		Long time2 = o.getDate().getTime();
		
		return time.compareTo(time2);
	}
	
	@Override
	public String toString() {
		return "Rate [date=" + date + ", price=" + price + ", vector=" + vector + ", currencyCode=" + currencyCode
				+ ", baseCurrencyCode=" + baseCurrencyCode + ", penultimateRate=" + penultimateRate + "]";
	}

}
