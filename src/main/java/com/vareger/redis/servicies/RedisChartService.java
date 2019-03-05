package com.vareger.redis.servicies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
//import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vareger.models.Rate;
import com.vareger.redis.daos.PairRedisDAO;
import com.vareger.redis.models.Chart;
import com.vareger.redis.models.CurrencyPair;
import com.vareger.redis.models.Schedule;
import com.vareger.servicies.TickerService;

@Service
public class RedisChartService {
	public static final String REDIS_KEY = "rate_chart";
	
	private final Logger log = LoggerFactory.getLogger(RedisEthereumService.class);
	
	@Autowired
	private TickerService tickerService;
	
	@Autowired
	@Qualifier("objectMapper")
	private ObjectMapper mapper;
	
	@Autowired
	private PairRedisDAO pairRedisDAO;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	private Date dateOfFirstRowInDb;
	
	@PostConstruct
	public void postConstructor() {
		dateOfFirstRowInDb = tickerService.getDateOfFirstRow();
		log.info("Date of first row in ticker table is: " + dateOfFirstRowInDb);
		Set<Object> keys = redisTemplate.opsForHash().keys(REDIS_KEY);
		for (Object key : keys) {
			redisTemplate.opsForHash().delete(REDIS_KEY, key);
			log.info("Chart key was delete from redis: " + key);
		}
	}
	
	public Chart getRatesByTimeInterval(String currCode, String baseCurrCode, Integer interval) throws IOException {
		String key = currCode + baseCurrCode + interval;
		
		HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
		String strChart = hashOps.get(REDIS_KEY, key);
		
		if ( strChart != null) 
			return mapper.readValue(strChart, Chart.class);
		
		return null;
	}
	
	public boolean countAndStoreChartDate(String currCode, String baseCurrCode, Schedule schedule) throws IOException {
		Integer interval = schedule.getInterval();
		log.info("" +
				": " + currCode + "_" + baseCurrCode + " interval: " + interval);
		String key = currCode + baseCurrCode + interval;
		
		HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
		String strChart = hashOps.get(REDIS_KEY, key);
		
		Chart chart = null;
		if ( strChart != null) 
			chart = mapper.readValue(strChart, Chart.class);
		
		if (chart == null) {
			chart = new Chart();
			chart.setCurrencyCode(currCode);
			chart.setBaseCurrencyCode(baseCurrCode);
			chart.setRates(new ArrayList<>());
		}
		
		List<Date> schedulePoints = schedule.getPointsDate();
		List<Rate> rates = chart.getRates();
		
		int firstValidIndex = -1;
		for (Date date : schedulePoints) {
			if (date.after(dateOfFirstRowInDb)) {
				firstValidIndex = schedulePoints.indexOf(date);
				break;
			}
		}
		
		for (int i = 0; i < firstValidIndex; i++) {
			schedulePoints.remove(0);
		}
		int newElementsCounter = 0;
		for (int i = 0; i < schedulePoints.size(); i++ ) {
			Date scheduleDate = schedulePoints.get(i);
			if (!removeUntilValid(scheduleDate, i, rates)) {
				Rate rate = findWhileFound(currCode, baseCurrCode, scheduleDate, 0);
				//If DB don't have got information
				if (rate == null) {
					rate = new Rate();
					rate.setCurrencyCode(currCode);
					rate.setBaseCurrencyCode(baseCurrCode);
					rate.setPrice(0.0);
				}
				rate.setDate(scheduleDate);
				//set format for Rate
				CurrencyPair rateCurrPair = new CurrencyPair(currCode, baseCurrCode);
				for (CurrencyPair currP : pairRedisDAO.getPairs()) {
					if (currP.equals(rateCurrPair)) {
						rate.setFormat(currP.getFormat());
						break;
					}
				}
				rates.add(rate);
				newElementsCounter += 1;
			}
		}
		//for recently added currency pairs
		replaceZero(rates);
		
		String chartStr = mapper.writeValueAsString(chart);
		redisTemplate.boundHashOps(REDIS_KEY).put(key, chartStr);
		log.info("Chart " + currCode + "/" + baseCurrCode + " interval: " + interval + " was updated. New elements was added: " + newElementsCounter);
		
		return (newElementsCounter != 0) ? true : false;
	}
	
	private void replaceZero(List<Rate> rates) {
		int firstNotZeroIndex = -1;
		for (int i = 0; i < rates.size(); i++) {
			Double price = rates.get(i).getPrice();
			if (price != null && price != 0.0) {
				firstNotZeroIndex = i;
				break;
			}
		}
		
		if (firstNotZeroIndex == -1 || firstNotZeroIndex == 0)
			return;
		
		double defaultPrice = rates.get(firstNotZeroIndex).getPrice();
		
		for (int i = 0; i < firstNotZeroIndex; i++) {
			rates.get(i).setPrice(defaultPrice);
		}
	}
	
	/**
	 * If find valid value return true.<br>
	 * validDate
	 * rates
	 * @return
	 */
	private boolean removeUntilValid(Date validDate, int startIndex, List<Rate> rates) {
			if (startIndex >= rates.size())
				return false;
			
			int arrSize = rates.size();
			for (int i = startIndex; i < arrSize; i++ ) {
				Rate rate = rates.get(startIndex);
				Date rateDate = rate.getDate();
				Double price = rate.getPrice();
				if (rateDate.equals(validDate) && price != null && price != 0.0)
					return true;
				
				rates.remove(startIndex);
			}
		
		return false;
	}
	
	private Rate findWhileFound(String currCode, String baseCurrCode, Date date, int attempt) {
		Rate rate = tickerService.getRateByTime(currCode, baseCurrCode, date);
		if (rate == null) {
			Date newDate = new Date(date.getTime() - 30 * 60 * 1000);
			if (newDate.after(dateOfFirstRowInDb) && attempt < 3) {
				log.debug("attempt: " + attempt);
				int newAttempt = attempt + 1;
				return findWhileFound(currCode, baseCurrCode, newDate, newAttempt);
			}
			else {
				log.info("Problem with get rate curr: " + currCode + "/" + baseCurrCode + " date: " + newDate) ;
				return null;
			}
		}
		
		return rate;
	}
	
//	private void healChat(Chart chart) {
//		List<Rate> rates = chart.getRates();
//		int firstValidIndex = 0;
//		for (int i = 0; i < rates.size(); i++ ) {
//			Rate rate = rates.get(i);
//			if (rate.getPrice() != null) {
//				firstValidIndex = i;
//				break;
//			}
//		}
//		
//		Rate firstValidRate = rates.get(firstValidIndex);
//		for (int i = 0; i < firstValidIndex; i++) {
//			rates.set(i, firstValidRate);
//		}
//	}
	

}
