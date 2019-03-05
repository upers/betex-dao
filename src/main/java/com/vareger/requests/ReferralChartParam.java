package com.vareger.requests;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vareger.json.serialization.DateToTimeestampSerializerNumeric;
import com.vareger.json.serialization.DoubleToDateDeserializer;
import com.vareger.validators.ValidAddress;
import com.vareger.validators.ValidChartReferralInterval;
import com.vareger.validators.ValidFromDate;
import com.vareger.validators.ValidToDate;

public class ReferralChartParam {
	
	@ValidAddress
	private String address;
	
	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	@JsonSerialize(using = DateToTimeestampSerializerNumeric.class)
	@ValidFromDate
	private Date from;
	
	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	@JsonSerialize(using = DateToTimeestampSerializerNumeric.class)
	@ValidToDate
	private Date to;
	
	@ValidChartReferralInterval
	private Long interval;
	
	public ReferralChartParam() {
		interval = 86400l;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String founderAddress) {
		this.address = founderAddress;
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

	public Long getInterval() {
		return interval;
	}

	public void setInterval(Long interval) {
		this.interval = interval;
	}

	@Override
	public String toString() {
		return "ReferralChartParam [founderAddress=" + address + ", from=" + from + ", to=" + to + ", interval=" + interval + "]";
	}
}
