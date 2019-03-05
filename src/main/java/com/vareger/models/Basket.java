package com.vareger.models;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vareger.jpa.converter.BigIntegerConverter;
import com.vareger.json.serialization.DateToTimeestampSerializerNumeric;
import com.vareger.json.serialization.DoubleToDateDeserializer;

@Entity
@Table(name = "basket")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Basket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "start_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	@JsonSerialize(using=DateToTimeestampSerializerNumeric.class)
	private Date openTime;

	@Column(name = "close_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	@JsonSerialize(using=DateToTimeestampSerializerNumeric.class)
	private Date closeTime;

	@Column(name = "end_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	@JsonSerialize(using=DateToTimeestampSerializerNumeric.class)
	private Date finishTime;

	@Column(name = "strike_rate")
	private Double strikeRate;

	@Column(name = "rate")
	private Double rate;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private BasketStatus status;
	
	@Column(name="live_seconds")
	private Long liveSeconds;
	
	@Column(name="open_seconds")
	private Long openSeconds;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "currency_id")
	@NotNull
	private Currency currency;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "base_currency_id")
	@NotNull
	private Currency baseCurrency;

	@Column
	private Long salt;

	@Column(name = "call_bids")
	@Convert(converter = BigIntegerConverter.class)
	private BigInteger callBids;
	
	/**
	 * amount of user witch have bid type: call
	 */
	@Column(name = "call_amount")
	private Integer callAmount;

	@Column(name = "put_bids")
	@Convert(converter = BigIntegerConverter.class)
	private BigInteger putBids;
	
	/**
	 * amount of user witch have bid type: put
	 */
	@Column(name = "put_amount")
	private Integer putAmount;
	
	@Column(name = "block_num")
	@Convert(converter = BigIntegerConverter.class)
	private BigInteger blockNum;
	
	@Column(name = "tx_index")
	private Integer txIndex;
	
	@Column(name = "tx_hash")
	private String txHash;

	@Column(name = "total_supply")
	@Convert(converter = BigIntegerConverter.class)
	private BigInteger totalSupply;
	
	@Column(name = "bot_total_supply")
	@Convert(converter = BigIntegerConverter.class)
	private BigInteger botTotalSupply;

	@Column(name = "real_supply")
	@Convert(converter = BigIntegerConverter.class)
	private BigInteger realSupply;
	
	@OneToMany(mappedBy = "basket")
	@JsonIgnore
	private List<UserBid> userBids;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getStrikeRate() {
		return strikeRate;
	}

	public void setStrikeRate(Double strikeRate) {
		this.strikeRate = strikeRate;
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

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public BasketStatus getStatus() {
		return status;
	}

	public void setStatus(BasketStatus status) {
		this.status = status;
	}

	public List<UserBid> getUserBids() {
		return userBids;
	}

	public void setUserBids(List<UserBid> userBids) {
		this.userBids = userBids;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Long getSalt() {
		return salt;
	}

	public void setSalt(Long salt) {
		this.salt = salt;
	}

	public BigInteger getCallBids() {
		return callBids;
	}

	public void setCallBids(BigInteger callBids) {
		this.callBids = callBids;
	}

	public BigInteger getPutBids() {
		return putBids;
	}

	public void setPutBids(BigInteger putBids) {
		this.putBids = putBids;
	}
	
	/**
	 * merge all attributes except: id, salt, userBids
	 *
	 */
	public void merge(Basket basket) {
		BigInteger callBids = basket.getCallBids();
		Date closeTime = basket.getCloseTime();
		Date finishTime = basket.getFinishTime();
		Date openTime = basket.getOpenTime();
		BigInteger putBids = basket.getPutBids();
		BasketStatus basketStatus = basket.getStatus();
		Double strikeRate = basket.getStrikeRate();
		Double rate = basket.getRate();
		Currency currency = basket.getCurrency();
		Currency baseCurrency = basket.getBaseCurrency();
		Integer callAmount = basket.getCallAmount();
		Integer putAmount = basket.getPutAmount();
		String txHash = basket.getTxHash();
		Long liveSeconds = basket.getLiveSeconds();
		Long openSeconds = basket.getOpenSeconds();
		Integer txIndex = basket.getTxIndex();
		BigInteger blockNum = basket.getBlockNum();
		BigInteger totalSupply = basket.getTotalSupply();
		BigInteger botTotalSupply = basket.getBotTotalSupply();
		BigInteger realSupply = basket.getRealSupply();
		
		if (txIndex != null)
			this.txIndex = txIndex;
		if (blockNum != null)
			this.blockNum = blockNum;
		if (liveSeconds != null)
			this.liveSeconds = liveSeconds;
		if (openSeconds != null)
			this.openSeconds = openSeconds;
		if (txHash != null)
			this.txHash = txHash;
		if (callAmount != null)
			this.callAmount = callAmount;
		if (putAmount != null)
			this.putAmount = putAmount;
		if (callBids != null)
			this.callBids = callBids;
		if (closeTime != null)
			this.closeTime = closeTime;
		if (finishTime != null)
			this.finishTime = finishTime;
		if (openTime != null)
			this.openTime = openTime;
		if (putBids != null)
			this.putBids = putBids;
		if (basketStatus != null)
			this.status = basketStatus;
		if (strikeRate != null)
			this.strikeRate = strikeRate;
		if (rate != null)
			this.rate = rate;
		if (currency != null)
			this.currency = currency;
		if (baseCurrency != null)
			this.baseCurrency = baseCurrency;
		if (totalSupply != null)
			this.totalSupply = totalSupply;
		if (botTotalSupply != null)
			this.botTotalSupply = botTotalSupply;
		if (realSupply != null)
			this.realSupply = realSupply;
	}

	public Integer getCallAmount() {
		return callAmount;
	}

	public void setCallAmount(Integer callAmount) {
		this.callAmount = callAmount;
	}

	public Integer getPutAmount() {
		return putAmount;
	}

	public void setPutAmount(Integer putAmount) {
		this.putAmount = putAmount;
	}

	public String getTxHash() {
		return txHash;
	}

	public void setTxHash(String transactionId) {
		this.txHash = transactionId;
	}

	public Long getLiveSeconds() {
		return liveSeconds;
	}

	public void setLiveSeconds(Long liveSeconds) {
		this.liveSeconds = liveSeconds;
	}

	public Long getOpenSeconds() {
		return openSeconds;
	}

	public void setOpenSeconds(Long openSeconds) {
		this.openSeconds = openSeconds;
	}

	public BigInteger getBlockNum() {
		return blockNum;
	}

	public void setBlockNum(BigInteger blockNum) {
		this.blockNum = blockNum;
	}

	public Integer getTxIndex() {
		return txIndex;
	}

	public void setTxIndex(Integer txIndex) {
		this.txIndex = txIndex;
	}

	public BigInteger getTotalSupply() {
		return totalSupply;
	}

	public void setTotalSupply(BigInteger totalSupply) {
		this.totalSupply = totalSupply;
	}

	public BigInteger getBotTotalSupply() {
		return botTotalSupply;
	}

	public void setBotTotalSupply(BigInteger botTotalSupply) {
		this.botTotalSupply = botTotalSupply;
	}

	public BigInteger getRealSupply() {
		return realSupply;
	}

	public void setRealSupply(BigInteger realSupply) {
		this.realSupply = realSupply;
	}

	@Override
	public String toString() {
		return "Basket [id=" + id + ", openTime=" + openTime + ", closeTime=" + closeTime + ", finishTime=" + finishTime + ", strikeRate="
				+ strikeRate + ", rate=" + rate + ", status=" + status + ", liveSeconds=" + liveSeconds + ", openSeconds=" + openSeconds
				+ ", currency=" + currency + ", baseCurrency=" + baseCurrency + ", salt=" + salt + ", callBids=" + callBids + ", callAmount="
				+ callAmount + ", putBids=" + putBids + ", putAmount=" + putAmount + ", blockNum=" + blockNum + ", txIndex=" + txIndex + ", txHash="
				+ txHash + ", totalSupply=" + totalSupply + ", botTotalSupply=" + botTotalSupply + ", realSupply=" + realSupply + "]";
	}



}
