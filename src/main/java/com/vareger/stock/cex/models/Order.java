package com.vareger.stock.cex.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vareger.json.serialization.DateToTimeestampSerializerNumeric;
import com.vareger.json.serialization.DoubleToDateDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
	private boolean complete;
	
	private String id;
	
	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	@JsonSerialize(using=DateToTimeestampSerializerNumeric.class)
	private Date time;
	
	private double pending;
	
	private double amount;
	
	private String type;
	
	private double price;

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public double getPending() {
		return pending;
	}

	public void setPending(double pending) {
		this.pending = pending;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Order [complete=" + complete + ", id=" + id + ", time=" + time + ", pending=" + pending + ", amount=" + amount + ", type="
				+ type + ", price=" + price + "]";
	}
	
	
}