package com.vareger.daos;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.vareger.config.ChartConfig;
import com.vareger.models.Ticker;

@Repository
public class TickerDAO extends AbstractDao<BigInteger, Ticker> {

	/**
	 * Get last tickers for each market stored in Data Base
	 * 
	 * currencyId
	 * baseCurrencyCode
	 * @return
	 */
	public List<Ticker> getLastMurketsTickers(Integer currencyId, Integer baseCurrencyId) {
		@SuppressWarnings("unchecked")
		List<Ticker> tickers = getSession()
				.createSQLQuery("SELECT * FROM  get_last_murkets_tickers(" + currencyId + ", " + baseCurrencyId + ");")
				.addEntity(Ticker.class).list();

		return tickers;
	}

	// /**
	// * Get course for input currencies or NULL if currencies ids are not present.
	// * Count avg course from present in DB murkets
	// *
	// * currencyId
	// * baseCurrencyId
	// * @return
	// */
	// public Rate getRate(Integer currencyId, Integer baseCurrencyId) {
	// Rate rate = (Rate) getSession().createSQLQuery("SELECT * FROM get_current_rate(:currId,
	// :baseCurrId)").addEntity(Rate.class)
	// .setParameter("currId", currencyId).setParameter("baseCurrId", baseCurrencyId).uniqueResult();
	//
	// return rate;
	// }

	/**
	 * Get last tickers from each market.<br>
	 * 
	 * currencyId
	 * baseCurrencyId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Ticker> getCurrentTickers(Integer currencyId, Integer baseCurrencyId) {
		return getSession().createSQLQuery("SELECT * FROM get_current_tickers(:currId, :baseCurrId)").addEntity(Ticker.class)
				.setParameter("currId", currencyId).setParameter("baseCurrId", baseCurrencyId).list();
	}

	/**
	 * Get tickers by input time from each market.<br>
	 * 
	 * currencyId
	 * baseCurrencyId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Ticker> getTickersByTime(Integer currencyId, Integer baseCurrencyId, Date date) {
		return getSession().createSQLQuery("SELECT * FROM get_tickers_by_time(:currId, :baseCurrId, :time)").addEntity(Ticker.class)
				.setParameter("currId", currencyId).setParameter("baseCurrId", baseCurrencyId).setParameter("time", date.getTime() / 1000l)
				.list();
	}

	// /**
	// * Get course by data for input currencies or NULL if currencies ids are not
	// * present. Count avg course from present in DB murkets
	// *
	// * date
	// * currencyId
	// * baseCurrencyId
	// * @return
	// */
	// public Rate getRateByTime(Integer currencyId, Integer baseCurrencyId, Date date) {
	// Rate rate = (Rate) getSession().createSQLQuery("SELECT * FROM get_rate_by_time(:currId, :baseCurrId,
	// :time)").addEntity(Rate.class)
	// .setParameter("currId", currencyId).setParameter("baseCurrId", baseCurrencyId).setParameter("time", date.getTime() /
	// 1000l).uniqueResult();
	//
	// return rate;
	// }

	/**
	 * Get penultimate tickers for each market by input currencies pair.
	 * 
	 * currencyId
	 * baseCurrencyId
	 * @return
	 */
	public List<Ticker> getPenultimateTickers(Integer currencyId, Integer baseCurrencyId) {
		@SuppressWarnings("unchecked")
		List<Ticker> tickers = getSession().createSQLQuery("SELECT * FROM get_penultimate_tickers(:currId, :baseCurrId)")
				.addEntity(Ticker.class).setParameter("currId", currencyId).setParameter("baseCurrId", baseCurrencyId).list();

		return tickers;
	}

	@SuppressWarnings("unchecked")
	public List<Ticker> getTickersByTimeInterval(Integer currId, Integer baseCurrId, Integer interval) {
		return getSession().createSQLQuery("SELECT * FROM get_tickers_by_interval(:currId, :baseCurrId, :interval, :limit)")
				.addEntity(Ticker.class).setParameter("currId", currId).setParameter("baseCurrId", baseCurrId)
				.setParameter("interval", interval).setParameter("limit", ChartConfig.CHART_POINTS_LIMIT).list();

	}

	public Ticker getFirstTicker() {
		return (Ticker) createEntityCriteria().addOrder(Order.asc("id")).setMaxResults(1).uniqueResult();
	}

}
