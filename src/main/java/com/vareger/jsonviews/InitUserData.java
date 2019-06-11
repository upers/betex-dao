package com.vareger.jsonviews;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vareger.json.serialization.DateToTimeestampSerializerNumeric;
import com.vareger.json.serialization.DoubleToDateDeserializer;
import com.vareger.models.Contract;
import com.vareger.models.Rate;
import com.vareger.models.Token;
import com.vareger.redis.models.CurrencyPair;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitUserData {
	
	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	@JsonSerialize(using=DateToTimeestampSerializerNumeric.class)
	private Date time;
	
	private ReferralsView referals;
	
	private Token token;
	
	private Contract betex;
	
	private Contract internalExchange;
	
	private Contract externalExchange;
	
	private Contract referral;
	
	private String internalRpcUrl;
	
	private String externalRpcUrl;
	
	private String defaultBrokerId;
	
	private String status;
	
	private String email;

	private String referralSuffix;
	
	private BigInteger volume;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	private List<CurrencyPair> currencyPairs;
	
	private List<Rate> pairs;
	
	private List<ExchangeView> exchange;
	
	private List<BasketConfigView> baskets;

	public List<Rate> getPairs() {
		return pairs;
	}

	public void setPairs(List<Rate> pairs) {
		this.pairs = pairs;
	}

	public List<ExchangeView> getExchange() {
		return exchange;
	}

	public void setExchange(List<ExchangeView> exchange) {
		this.exchange = exchange;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public List<BasketConfigView> getBaskets() {
		return baskets;
	}

	public void setBaskets(List<BasketConfigView> baskets) {
		this.baskets = baskets;
	}

	public ReferralsView getReferals() {
		return referals;
	}

	public void setReferals(ReferralsView referals) {
		this.referals = referals;
	}

	public Contract getBetex() {
		return betex;
	}

	public void setBetex(Contract betex) {
		this.betex = betex;
	}

	public Contract getReferral() {
		return referral;
	}

	public void setReferral(Contract referral) {
		this.referral = referral;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Contract getInternalExchange() {
		return internalExchange;
	}

	public void setInternalExchange(Contract internalExchange) {
		this.internalExchange = internalExchange;
	}

	public Contract getExternalExchange() {
		return externalExchange;
	}

	public void setExternalExchange(Contract externalExchange) {
		this.externalExchange = externalExchange;
	}

	public String getInternalRpcUrl() {
		return internalRpcUrl;
	}

	public void setInternalRpcUrl(String internalRpcUrl) {
		this.internalRpcUrl = internalRpcUrl;
	}

	public String getExternalRpcUrl() {
		return externalRpcUrl;
	}

	public void setExternalRpcUrl(String externalRpcUrl) {
		this.externalRpcUrl = externalRpcUrl;
	}

	public String getDefaultBrokerId() {
		return defaultBrokerId;
	}

	public void setDefaultBrokerId(String defaultBrokerId) {
		this.defaultBrokerId = defaultBrokerId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigInteger getVolume() {
		return volume;
	}

	public void setVolume(BigInteger volume) {
		this.volume = volume;
	}

	public List<CurrencyPair> getCurrencyPairs() {
		return currencyPairs;
	}

	public void setCurrencyPairs(List<CurrencyPair> currencyPairs) {
		this.currencyPairs = currencyPairs;
	}

	public String getReferralSuffix() {
		return referralSuffix;
	}

	public void setReferralSuffix(String referralSuffix) {
		this.referralSuffix = referralSuffix;
	}
}
