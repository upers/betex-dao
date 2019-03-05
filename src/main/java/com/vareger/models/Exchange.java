package com.vareger.models;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vareger.jpa.converter.BigIntegerConverter;
import com.vareger.json.serialization.DoubleToDateDeserializer;
import com.vareger.service.tools.HexUtils;
import com.vareger.web3j.EthNet;

@Entity
@Table
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_EMPTY)
public class Exchange {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private BigInteger id;
	
	@Column(name="user_tx_id")
	private String userTxHash;
	
	@Column(name="oraclize_tx_id")
	private String oraclizeTxHash;
	
	@Column(name="user_address")
	private String userAddress;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "currency_id")
	@NotNull
	private Currency currency;
	
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private ExchangeType type;
	
	@Column(name = "amount")
	@Convert(converter = BigIntegerConverter.class)
	private BigInteger amount;
	
	@Column(name = "wei")
	@Convert(converter = BigIntegerConverter.class)
	private BigInteger wei;
	
	@Column(name = "clear_wei")
	@Convert(converter = BigIntegerConverter.class)
	private BigInteger clearWei;
	
	@Column(name = "rate")
	private Double rate;
	
	@Column(name = "time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	private Date time;
	
	@NotNull
	@Column(name = "confirmed", columnDefinition = "boolean default false")
	private boolean confirmed;
	
	@Column(name = "total_supply")
	@Convert(converter = BigIntegerConverter.class)
	private BigInteger totalSupply;
	
	@NotNull
	@Column(name = "network")
	@Enumerated(EnumType.STRING)
	private EthNet ethNet;
	
	@Transient
	@JsonProperty("expectedConfirmations")
	private BigInteger expectedConfirmations;
	
	@Transient
	@JsonProperty("confirmations")
	private BigInteger confirmations;
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getUserTxHash() {
		return userTxHash;
	}

	public void setUserTxHash(String userTxHash) {
		this.userTxHash = userTxHash;
	}

	public String getOraclizeTxHash() {
		return oraclizeTxHash;
	}

	public void setOraclizeTxHash(String oraclizeTxHash) {
		this.oraclizeTxHash = oraclizeTxHash;
	}
	
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
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

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = HexUtils.getHexClear(userAddress);
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

	public void setTime(Date date) {
		this.time = date;
	}
	

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public BigInteger getClearWei() {
		return clearWei;
	}

	public void setClearWei(BigInteger clearWei) {
		this.clearWei = clearWei;
	}

	public BigInteger getWei() {
		return wei;
	}

	public void setWei(BigInteger wei) {
		this.wei = wei;
	}

	
	public void merge(Exchange exchange) {
		String userTxHash = exchange.getUserTxHash();
		String oraclizeTxHash = exchange.getOraclizeTxHash();
		String userAddress = exchange.getUserAddress();
		Currency currency = exchange.getCurrency();
		ExchangeType type = exchange.getType();
		BigInteger amount = exchange.getAmount();
		BigInteger wei = exchange.getWei();
		BigInteger clearWei = exchange.getClearWei();
		Double rate = exchange.getRate();
		Date time = exchange.getTime();
		EthNet ethNet = exchange.getEthNet();
		boolean confirmed = exchange.isConfirmed();
		BigInteger expectedConfirmations = exchange.getExpectedConfirmations();
		BigInteger confirmations = exchange.getConfirmations();
		BigInteger totalSupply = exchange.getTotalSupply();
		
		if (userTxHash != null)
			this.userTxHash = userTxHash;
		if (oraclizeTxHash != null)
			this.oraclizeTxHash = oraclizeTxHash;
		if (userAddress != null)
			this.userAddress = userAddress;
		if (currency != null)
			this.currency = currency;
		if (type != null)
			this.type = type;
		if (amount != null)
			this.amount = amount;
		if (wei != null)
			this.wei = wei;
		if (clearWei != null)
			this.clearWei = clearWei;
		if (rate != null)
			this.rate = rate;
		if (time != null)
			this.time = time;
		if (ethNet != null)
			this.ethNet = ethNet;
		if (expectedConfirmations != null)
			this.expectedConfirmations = expectedConfirmations;
		if (confirmations != null)
			this.confirmations = confirmations;
		if (totalSupply != null)
			this.totalSupply = totalSupply;
		
		this.confirmed = confirmed;
	}

	public EthNet getEthNet() {
		return ethNet;
	}

	public void setEthNet(EthNet ethNet) {
		this.ethNet = ethNet;
	}

	public BigInteger getExpectedConfirmations() {
		return expectedConfirmations;
	}

	public void setExpectedConfirmations(BigInteger expectedConfirmations) {
		this.expectedConfirmations = expectedConfirmations;
	}

	public BigInteger getConfirmations() {
		return confirmations;
	}

	public void setConfirmations(BigInteger confirmations) {
		this.confirmations = confirmations;
	}

	public BigInteger getTotalSupply() {
		return totalSupply;
	}

	public void setTotalSupply(BigInteger totalSupply) {
		this.totalSupply = totalSupply;
	}

	@Override
	public String toString() {
		return "Exchange [id=" + id + ", userTxHash=" + userTxHash + ", oraclizeTxHash=" + oraclizeTxHash + ", userAddress=" + userAddress
				+ ", currency=" + currency + ", type=" + type + ", amount=" + amount + ", wei=" + wei + ", clearWei=" + clearWei + ", rate="
				+ rate + ", time=" + time + ", confirmed=" + confirmed + ", ethNet=" + ethNet + ", expectedConfirmations="
				+ expectedConfirmations + ", confirmations=" + confirmations + ", totalSupply=" + totalSupply + "]";
	}

}
