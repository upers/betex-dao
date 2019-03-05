package com.vareger.servicies;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.vareger.models.Rate;
import com.vareger.models.Ticker;

public interface TickerService extends Service<BigInteger, Ticker> {
	
	/**
	 * Get last tickers for each market stored in Data Base by currency pair.<br>
	 * If currency code invalid return empty List.
	 * currencyCode
	 * baseCurrencyCode
	 * @return {@link java.util.List} of {@link Ticker}
	 */
	List<Ticker> getLastMurketsTickers(String currencyCode, String baseCurrencyCode);
	
	/**
	 * Get penultimate {@link Rate} for input currencies or NULL if currencies codes are not present.
	 * {@link Rate} avg course from present in DB murkets 
	 * currencyId
	 * baseCurrencyId
	 * @return
	 */
	Rate getPenultimateRate(String currencyCode, String baseCurrencyCode);
	
	/**
	 * Get current {@link Rate} for input currencies or NULL if currencies ids are not present
	 * Count avg course from present in DB murkets  
	 * currencyId
	 * baseCurrencyId
	 * @return
	 */
	Rate getRate(String currencyId, String baseCurrencyId);
	
	/**
	 * Get current {@link Rate} by date for input currencies or NULL if currencies ids are not present
	 * Count avg course from present in DB murkets  
	 * currencyId
	 * baseCurrencyId
	 * date
	 * @return
	 */
	Rate getRateByTime(String currencyCode, String baseCurrencyCode, Date date);
	
	Date getDateOfFirstRow();
	/**
	 * Get List of {@link Rate} for input currencies from the <code>fromDate</code> argument to the <code>toDate</code> argument.<br>
	 * Using the interval
	 * fromDate {@link java.ulit.Date}
	 * toDate {@link java.ulit.Date}
	 * interval
	 * currCode
	 * baseCurrCode
	 * @return
	 */
//	 List<Rate> getRatesByTimeInterval(Date fromDate, Date toDate, Integer interval, String currCode, String baseCurrCode);
	
	
	/**
	 * Get List of {@link Rate} for input currencies by Interval using limit in <code>TickerDAO.CHART_POINTS_LIMIT</code><br>
	 * Using the interval
	 * currCode
	 * baseCurrCode
	 * interval
	 * @return
	 */
//	List<Rate> getRatesByTimeInterval(String currCode, String baseCurrCode, Integer interval);
	 
}
