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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vareger.jpa.converter.BigIntegerConverter;
import com.vareger.json.serialization.DateToTimeestampSerializerNumeric;
import com.vareger.json.serialization.DoubleToDateDeserializer;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
@Table(name = "userbid")
public class UserBid {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "address")
	private String address;
	
	@Column(name = "win_amount")
	@Convert(converter = BigIntegerConverter.class)
	private BigInteger winAmount;
	
	@Column(name = "tx_hash")
	private String txHash;
	
	@Column(name = "amount")
	@Convert(converter = BigIntegerConverter.class)
	private BigInteger amount;

	@Column(name = "bid_type")
	@Enumerated(EnumType.STRING)
	private BidType bidType;
	
	@Column(name = "bid_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	@JsonSerialize(using=DateToTimeestampSerializerNumeric.class)
	private Date bidDate;
	
	@Column(name = "reward_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	@JsonSerialize(using=DateToTimeestampSerializerNumeric.class)
	private Date rewardDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(columnDefinition = "basket_id")
	private Basket basket;
	
	@OneToOne
	@JoinColumn(columnDefinition = "broker_id")
	private Broker broker;
	
	@NotNull
	@Column(name = "confirmed", columnDefinition = "boolean default false")
	private boolean confirmed;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigInteger getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(BigInteger winAmount) {
		this.winAmount = winAmount;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	public BidType getBidType() {
		return bidType;
	}

	public void setBidType(BidType bidType) {
		this.bidType = bidType;
	}

	public Date getBidDate() {
		return bidDate;
	}

	public void setBidDate(Date bidDate) {
		this.bidDate = bidDate;
	}

	public Date getRewardDate() {
		return rewardDate;
	}

	public void setRewardDate(Date rewardDate) {
		this.rewardDate = rewardDate;
	}

	public BigInteger getAmount() {
		return amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}

	/**
	 * merge all attributes except: id, basket
	 *
	 */
	public void merge(UserBid userBid) {
		String address = userBid.getAddress();
		BigInteger amount = userBid.getAmount();
		Date bidDate = userBid.getBidDate();
		BidType bidType = userBid.getBidType();
		Date rewardDate = userBid.getRewardDate();
		BigInteger winAmount = userBid.getWinAmount();
		String txHash = userBid.getTxHash();
		Broker broker = userBid.getBroker();
		boolean confirmed = userBid.isConfirmed();
		
		if (txHash != null)
			this.txHash = txHash;
		if (address != null)
			this.address = address;
		if (amount != null)
			this.amount = amount;
		if (bidDate != null)
			this.bidDate = bidDate;
		if (bidType != null)
			this.bidType = bidType;
		if (rewardDate != null)
			this.rewardDate = rewardDate;
		if (winAmount != null)
			this.winAmount = winAmount;
		if (broker != null)
			this.broker = broker;
		
		this.confirmed = confirmed;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTxHash() {
		return txHash;
	}

	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Broker getBroker() {
		return broker;
	}

	public void setBroker(Broker broker) {
		this.broker = broker;
	}

	@Override
	public String toString() {
		return "UserBid [id=" + id + ", address=" + address + ", winAmount=" + winAmount + ", txHash=" + txHash + ", amount=" + amount + ", bidType="
				+ bidType + ", bidDate=" + bidDate + ", rewardDate=" + rewardDate + ", basket=" + basket + ", broker=" + broker + ", confirmed="
				+ confirmed + "]";
	}

}
