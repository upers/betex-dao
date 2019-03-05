package com.vareger.models;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vareger.jpa.converter.BigIntegerConverter;
import com.vareger.json.serialization.DoubleToDateDeserializer;

@Entity
@Table(name = "referral_income")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ReferralIncome {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Integer id;
	
	@Column(name = "amount")
	@Convert(converter = BigIntegerConverter.class)
	private BigInteger profit;
	
	@Column(name = "referral_address")
	private String address;
	
	@Column(name = "founder_address")
	private String founderAddress;
	
	@Column(name = "time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	private Date timestamp;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(columnDefinition = "id")
	private Broker broker;
	
	@Column(name = "tx_hash")
	private String txHash;
	
	public ReferralIncome() {}
	
	public ReferralIncome(String referralAddress, String founderAddress, BigInteger amount) {
		this.address = referralAddress;
		this.founderAddress = founderAddress;
		this.profit = amount;
	}
	
	public ReferralIncome(String referralAddress, String founderAddress, BigInteger amount, Date timestamp) {
		this.address = referralAddress;
		this.founderAddress = founderAddress;
		this.profit = amount;
		this.timestamp = timestamp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFounderAddress() {
		return founderAddress;
	}

	public void setFounderAddress(String founderAddress) {
		this.founderAddress = founderAddress;
	}

	public BigInteger getProfit() {
		return profit;
	}

	public void setProfit(BigInteger profit) {
		this.profit = profit;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "ReferralIncome [id=" + id + ", profit=" + profit + ", address=" + address + ", founderAddress=" + founderAddress + ", timestamp="
				+ timestamp + ", broker=" + broker + ", txHash=" + txHash + "]";
	}
	
}
