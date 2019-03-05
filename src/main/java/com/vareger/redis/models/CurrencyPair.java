package com.vareger.redis.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vareger.models.Pair;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class CurrencyPair implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static String KEY = "available_currencies_pairs";

	private String id;

	private String name;
	
	private String currencyName;
	
	private String baseCurrencyName;
	
	private String currencyCode;
	
	private String baseCurrencyCode;
	
	private String format;
	
	private Boolean enabled;
	
	public CurrencyPair() {
		
	}
	
	public CurrencyPair(String currencyCode, String baseCurrencyCode) {
		this.currencyCode = currencyCode;
		this.baseCurrencyCode = baseCurrencyCode;
	}
	
	public CurrencyPair(Pair pair) {
		this.currencyCode = pair.getCurrency().getCode();
		this.baseCurrencyCode = pair.getBaseCurrency().getCode();
		this.format = pair.getFormat();
		this.currencyName = pair.getCurrency().getName();
		this.baseCurrencyName = pair.getBaseCurrency().getName();
		this.enabled = pair.getEnabled();
		this.name = currencyName + "/" + baseCurrencyName;
		this.id = currencyCode + "_" + baseCurrencyCode;
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
	
	/**
	 * Convert path URI to object {@link CurrencyPair}.<br>
	 * Input parameter validation present in method.<br>
	 * pathPair
	 * @return {@link CurrencyPair} if input parameter incorrect <i>NULL<i>
	 */
	public static CurrencyPair pathToPair(String pathPair) {
		if (pathPair == null)
			return null;
		
		if (!pathPair.contains("_"))
			return null;
		
		String[] pairArr = pathPair.split("_");
		if (pairArr.length != 2)
			return null;
		
		String currencyCode = pairArr[0];
		String baseCurrencyCode = pairArr[1];
		
		if (currencyCode.equals(baseCurrencyCode))
			return null;
		
		return new CurrencyPair(currencyCode, baseCurrencyCode);
	}
	
	/**
	 * Convert currencies codes to Path URI
	 * pathPair
	 * @return {@link CurrencyPair} if input parameter incorrect <i>NULL<i>
	 */
	public static String currenciesToPath(String currCode, String baseCurrCode) {
		if (currCode == null || baseCurrCode == null)
			return null;
		
		
		return currCode + "_" + baseCurrCode.toLowerCase();
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getBaseCurrencyName() {
		return baseCurrencyName;
	}

	public void setBaseCurrencyName(String baseCurrencyName) {
		this.baseCurrencyName = baseCurrencyName;
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
	@Override
	public String toString() {
		return this.currencyCode + this.baseCurrencyCode;
	}
	
	@Override
	public boolean equals(Object o1) {
		if (o1 == this)
			return true;
		
		if (o1 instanceof CurrencyPair) {
			CurrencyPair currPair1 = (CurrencyPair) o1;
			if (currPair1.getCurrencyCode().equals(this.currencyCode) && currPair1.getBaseCurrencyCode().equals(this.baseCurrencyCode))
				return true;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		String result = this.currencyCode + this.baseCurrencyCode;
		
		return result.hashCode();
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean active) {
		this.enabled = active;
	}
	
}
