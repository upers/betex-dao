package com.vareger.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "murket")
@JsonInclude(Include.NON_NULL)
public class Murket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	/**
	 * Base url which returns data for the last trade transaction.<br>
	 * This url have contain two parameters ${current} and ${base_currect} <br>
	 * which will be replaced in the application for currency codes.
	 */
	@Column(name = "current_trade_price_url")
	private String tickerURL;

	/**
	 * Root json element in response which contain Stats data
	 */
	@Column(name = "path_to_root_element")
	private String pathToRootElement;

	@Column(name = "ticker_load_mode", nullable = false)
	private String tickerLoadMode;

	/**
	 * If the vale is true then parameters ${current} and ${base_currect} in
	 * currency trade price URL have to be in UPPERCASAE else in LOWCASE.
	 */
	@Column(name = "is_currency_pair_uppercase", nullable = false)
	private boolean isCurrencyPairUppercase;
	
	/**
	 * Is murket enabled in application.<br>
	 * If value true use it.
	 */
	@Column(name = "is_enabled", nullable = false)
	private boolean isEnabled;
	
	/**
	 * Is replace USD to USDT in murket.
	 */
	@Column(name = "is_replace_usd_usdt", nullable = false)
	private boolean isReplaceUsdUsdt;

	/**
	 * User agent can be null.<br>
	 * If not null service have to use user agent in request headers
	 */
	@Column(name = "user_agent")
	private String useUserAgent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTickerURL() {
		return tickerURL;
	}

	public void setTickerURL(String tickerURL) {
		this.tickerURL = tickerURL;
	}

	public String getPathToRootElement() {
		return pathToRootElement;
	}

	public void setPathToRootElement(String pathToRootElement) {
		this.pathToRootElement = pathToRootElement;
	}

	public boolean isCurrencyPairUppercase() {
		return isCurrencyPairUppercase;
	}

	public void setCurrencyPairUppercase(boolean isCurrencyPairUperrcase) {
		this.isCurrencyPairUppercase = isCurrencyPairUperrcase;
	}

	/**
	 * Prepare path to current murket JSON ticker root element.<br>
	 * Replace expressions ${currency} to currencyCode.<br>
	 * Replace expressions ${base_currency} to baseCurrencyCode.<br>
	 * And make expressions in correct symbol case.<br>
	 * 
	 * currencyCode
	 * baseCurrencyCode
	 * @return
	 */
	public String preparePathToRootEl(String currencyCode, String baseCurrencyCode) {
		if (pathToRootElement == null)
			return null;

		if (name.equals("kraken")) {
			if (currencyCode.equals("btc"))
				currencyCode = "xbt";
			if (baseCurrencyCode.equals("btc"))
				baseCurrencyCode = "xbt";
		}
		
		if (isCurrencyPairUppercase) {
			currencyCode = currencyCode.toUpperCase();
			baseCurrencyCode = baseCurrencyCode.toUpperCase();
		} else {
			currencyCode = currencyCode.toLowerCase();
			baseCurrencyCode = baseCurrencyCode.toLowerCase();
		}
		
		if (currencyCode.equals("LTC") && baseCurrencyCode.equals("XBT") && name.equals("kraken"))
			return pathToRootElement.replace("${currency}", currencyCode).replace("Z${base_currency}", "X" + baseCurrencyCode);
		else
			return pathToRootElement.replace("${currency}", currencyCode).replace("${base_currency}", baseCurrencyCode);
	}

	/**
	 * Prepare current murket ticker URL.<br>
	 * Replace expressions ${currency} to currencyCode.<br>
	 * Replace expressions ${base_currency} to baseCurrencyCode.<br>
	 * And make expressions in correct symbol case.<br>
	 * 
	 * currencyCode
	 * baseCurrencyCode
	 * @return
	 */
	public String prepareTickerURL(String currencyCode, String baseCurrencyCode) {
		if (isReplaceUsdUsdt) {
			if (currencyCode.toLowerCase().equals("usd"))
				currencyCode = "usdt";
			if (baseCurrencyCode.toLowerCase().equals("usd"))
				baseCurrencyCode = "usdt";
		}
		if (name.equals("kraken")) {
			if (currencyCode.equals("btc"))
				currencyCode = "xbt";
			if (baseCurrencyCode.equals("btc"))
				baseCurrencyCode = "xbt";
		}
		
		if (isCurrencyPairUppercase) {
			currencyCode = currencyCode.toUpperCase();
			baseCurrencyCode = baseCurrencyCode.toUpperCase();
		} else {
			currencyCode = currencyCode.toLowerCase();
			baseCurrencyCode = baseCurrencyCode.toLowerCase();
		}
		
		return tickerURL.replace("${currency}", currencyCode).replace("${base_currency}", baseCurrencyCode);
	}

	public String getTickerLoadMode() {
		return tickerLoadMode;
	}

	public void setTickerLoadMode(String tickerLoadMode) {
		this.tickerLoadMode = tickerLoadMode;
	}

	public String getUseUserAgent() {
		return useUserAgent;
	}

	public void setUseUserAgent(String useUserAgent) {
		this.useUserAgent = useUserAgent;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean isReplaceUsdUsdt() {
		return isReplaceUsdUsdt;
	}

	public void setReplaceUsdUsdt(boolean isReplaceUsdUsdt) {
		this.isReplaceUsdUsdt = isReplaceUsdUsdt;
	}

}
