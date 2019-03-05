package com.vareger.servicies.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.CurrencyDAO;
import com.vareger.daos.TickerDAO;
import com.vareger.models.Currency;
import com.vareger.models.Rate;
import com.vareger.models.Ticker;
import com.vareger.servicies.TickerService;

@Service
@Transactional
public class TickerServiceImpl extends ServiceImpl<BigInteger, Ticker> implements TickerService {
	
	private static final Logger log = LoggerFactory.getLogger(TickerServiceImpl.class);
	
	private TickerDAO tickerDAO;
	
	@Autowired
	private CurrencyDAO currencyDAO;
	
	@Autowired
	public TickerServiceImpl(@Qualifier("tickerDAO")AbstractDao<BigInteger, Ticker> tickerDAO) {
		super(tickerDAO);
		this.tickerDAO = (TickerDAO) tickerDAO;
	}
	
	@Override
	public List<Ticker> getLastMurketsTickers(String currencyCode, String baseCurrencyCode) {
		Currency currency = currencyDAO.findByCode(currencyCode);
		Currency baseCurrency = currencyDAO.findByCode(baseCurrencyCode);
		if (currency != null && baseCurrency != null)
			return tickerDAO.getLastMurketsTickers(currency.getId(), baseCurrency.getId());

		return new ArrayList<>(); 
	}
	
	@Override
	public Rate getPenultimateRate(String currencyCode, String baseCurrencyCode) {
		Currency currency = currencyDAO.findByCode(currencyCode);
		Currency baseCurrency = currencyDAO.findByCode(baseCurrencyCode);
		if (currency != null && baseCurrency != null) {
			List<Ticker> tickers = tickerDAO.getPenultimateTickers(currency.getId(), baseCurrency.getId());
			Double dividend = tickers.stream().mapToDouble((ticker) -> ticker.getVolume() * ticker.getLastPrice()).sum();
			Double divider = tickers.stream().mapToDouble((ticker) -> ticker.getVolume()).sum();
			Double avgPrice = dividend / divider;
			
			Rate course = new Rate();
			course.setDate(new Date());
			course.setCurrencyCode(currencyCode);
			course.setBaseCurrencyCode(baseCurrencyCode);
			course.setPrice(avgPrice);
			
			return course;
		}
		
		return null;
	}
	
	@Override
	public Rate getRate(String currencyCode, String baseCurrencyCode) {
		Currency currency = currencyDAO.findByCode(currencyCode);
		Currency baseCurrency = currencyDAO.findByCode(baseCurrencyCode);
		if (currency != null && baseCurrency != null) {
			List<Ticker> tickers = tickerDAO.getCurrentTickers(currency.getId(), baseCurrency.getId());
			Double dividend = tickers.stream().mapToDouble((ticker) -> ticker.getVolume() * ticker.getLastPrice()).sum();
			Double divider = tickers.stream().mapToDouble((ticker) -> ticker.getVolume()).sum();
			Double avgPrice = dividend / divider;
			
			Rate course = new Rate();
			course.setDate(new Date());
			course.setCurrencyCode(currencyCode);
			course.setBaseCurrencyCode(baseCurrencyCode);
			course.setPrice(avgPrice);
			
			return course;
		}
		
		return null;
	}
	
	
	@Override
	public Rate getRateByTime(String currencyCode, String baseCurrencyCode, Date date) {
		Currency currency = currencyDAO.findByCode(currencyCode);
		Currency baseCurrency = currencyDAO.findByCode(baseCurrencyCode);
		log.debug("Get rate by time currency: " + currency + " baseCurrency: " + baseCurrency);
		if (currency != null && baseCurrency != null && date != null) {
			List<Ticker> tickers = tickerDAO.getTickersByTime(currency.getId(), baseCurrency.getId(), date);
			log.debug("Tickers size: " + tickers.size());
			if (tickers.isEmpty())
				return null;
			
			Double dividend = tickers.stream().mapToDouble((ticker) -> ticker.getVolume() * ticker.getLastPrice()).sum();
			Double divider = tickers.stream().mapToDouble((ticker) -> ticker.getVolume()).sum();
			Double avgPrice = dividend / divider;
			
			Rate course = new Rate();
			course.setDate(date);
			course.setCurrencyCode(currencyCode);
			course.setBaseCurrencyCode(baseCurrencyCode);
			course.setPrice(avgPrice);
			
			return course;
		}
		
		return null;
	}

	@Override
	public Date getDateOfFirstRow() {
		Ticker ticker = tickerDAO.getFirstTicker();
		
		return ticker.getDate();
	}

}
