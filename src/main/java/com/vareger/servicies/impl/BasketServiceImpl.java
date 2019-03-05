package com.vareger.servicies.impl;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.BasketDAO;
import com.vareger.daos.CurrencyDAO;
import com.vareger.models.Basket;
import com.vareger.models.BasketStatus;
import com.vareger.models.BasketWrapper;
import com.vareger.models.Currency;
import com.vareger.redis.models.CurrencyPair;
import com.vareger.requests.BasketParam;
import com.vareger.servicies.BasketService;

@Service
@Transactional
public class BasketServiceImpl extends ServiceImpl<Integer, Basket> implements BasketService {
	
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(BasketServiceImpl.class);
	
	private BasketDAO basketDAO;

	@Autowired
	private CurrencyDAO currencyDAO;

	public BasketServiceImpl(AbstractDao<Integer, Basket> genericDao) {
		super(genericDao);
		basketDAO = (BasketDAO) genericDao;
	}

	public List<Basket> getByStatus(String status) {
		return basketDAO.getByStatus(status);
	}

	/**
	 * Check is basket with input salt present.<br>
	 * If input salt == null then method will return true
	 * 
	 * salt
	 * @return
	 */
	public boolean isSaltExist(Long salt) {
		if (salt == null)
			return true;

		Basket persistBasket = basketDAO.getBySalt(salt);
		if (persistBasket == null)
			return false;
		else
			return true;
	}

	@Override
	public boolean isSaltExist(Basket basket) {
		if (basket == null)
			return true;

		return isSaltExist(basket.getSalt());
	}

	@Override
	public boolean isSaltExist(BasketWrapper basketWrapper) {
		if (basketWrapper == null)
			return true;

		Basket basket = basketWrapper.getBasket();
		if (basket == null)
			return true;

		return isSaltExist(basket.getSalt());
	}
	
	@Override
	public void updateFinishRateBySalt(Long salt, Double resultRate) {
		if (salt == null)
			return ;
		
		if (resultRate == null)
			return;

		Basket persistBasket = basketDAO.getBySalt(salt);
		if (persistBasket == null)
			return ;
		
		persistBasket.setRate(resultRate);
	}

	@Override
	@Transactional(isolation=Isolation.SERIALIZABLE)
	public Basket updateBySalt(Basket basket, Long salt) {
		if (salt == null)
			return null;

		Basket persistBasket = basketDAO.getBySalt(salt);
		if (persistBasket == null) {
			log.error("Basket with salt: " + salt + " is not exist.");
			return null;
		}
		
		// If persist basket is newer than input return
		BigInteger persBlockNum = (persistBasket.getBlockNum() == null) ? BigInteger.valueOf(-1) : persistBasket.getBlockNum();
		BigInteger blokNum = (basket.getBlockNum() == null) ? BigInteger.valueOf(Long.MAX_VALUE) : basket.getBlockNum();
		Integer persTxIndex = (persistBasket.getTxIndex() == null) ? -1 : persistBasket.getTxIndex();
		Integer txIndex = (basket.getTxIndex() == null) ? Integer.MAX_VALUE : basket.getTxIndex();

		int compareResult = persBlockNum.compareTo(blokNum);
		if (compareResult > 0) {
			return null;
		} else if (compareResult == 0) {
			if (persTxIndex > txIndex)
				return null;
		}
		// Deny to decrement status only increment
		BasketStatus persStatus = persistBasket.getStatus();
		BasketStatus status = basket.getStatus();
		if (persStatus != null && status != null && persStatus.ordinal() > status.ordinal())
			basket.setStatus(persStatus);
		
		persistBasket.merge(basket);
		
		return persistBasket;
	}

	public Basket getBySalt(Long salt) {
		return basketDAO.getBySalt(salt);
	}

	@Override
	public List<Basket> findAllBy24Hour() {
		List<Basket> baskets = basketDAO.findAllBy24Hour();

		return baskets;
	}

	@Override
	public List<Basket> findAllBy24Hour(Integer offset, Integer limit) {
		List<Basket> baskets = basketDAO.findAllBy24Hour(offset, limit);

		return baskets;
	}

	@Override
	public List<Basket> findNotFinished() {
		return basketDAO.findNotFinished();
	}

	@Override
	public List<Basket> findByParam(BasketParam param) {
		Currency currency = null;
		Currency baseCurrency = null;

		String pairId = param.getPairId();
		CurrencyPair currencyPair = CurrencyPair.pathToPair(pairId);
		if (currencyPair != null) {
			String currencyCode = currencyPair.getCurrencyCode();
			currency = currencyDAO.findByCode(currencyCode);
			String baseCurrencyCode = currencyPair.getBaseCurrencyCode();
			baseCurrency = currencyDAO.findByCode(baseCurrencyCode);

		}

		return basketDAO.findByParam(currency, baseCurrency, param);
	}
	
	/**
	 * This method find not finished basket.<br>
	 * End fetch all bids in Eager mode.
	 * @return
	 */
	@Override
	public List<Basket> findFinishedEager(int limit) {
		return basketDAO.findFinishedEager(limit);
	}
	
	@Override
	public List<Basket> findByParamFrontView(BasketParam param) {
		Currency currency = null;
		Currency baseCurrency = null;

		String pairId = param.getPairId();
		CurrencyPair currencyPair = CurrencyPair.pathToPair(pairId);
		if (currencyPair != null) {
			String currencyCode = currencyPair.getCurrencyCode();
			currency = currencyDAO.findByCode(currencyCode);
			String baseCurrencyCode = currencyPair.getBaseCurrencyCode();
			baseCurrency = currencyDAO.findByCode(baseCurrencyCode);

		}
		
		List<Basket> baskets = basketDAO.findByParamFrontView(currency, baseCurrency, param);
		BasketStatus status = param.getState();
		if (status != null) {
			for (Basket basket : baskets) {
				basketDAO.evict(basket);
				basket.setStatus(status);
			}
		}
		
		return baskets;
	}

	@Override
	public void generateSaltAndSave(Basket basket) {
		do {
			Long salt = generateSalt(basket);
			basket.setSalt(salt);
		} while (isSaltExist(basket));

		save(basket);
	}

	private Long generateSalt(Basket basket) {
		Double strikeRate = basket.getStrikeRate();
		Currency currency = basket.getCurrency();
		Currency baseCurrency = basket.getBaseCurrency();

		if (strikeRate == null)
			throw new IllegalStateException("Generate salt for basket impossible, strikeRate MUST not be null");
		if (currency == null)
			throw new IllegalStateException("Generate salt for basket impossible, currency MUST not be null");
		if (baseCurrency == null)
			throw new IllegalStateException("Generate salt for basket impossible, baseCurrency MUST not be null");

		Long time = System.currentTimeMillis();
		int hashCode = new String(time.toString() + currency.getCode() + baseCurrency.getCode() + strikeRate.toString()).hashCode();

		return Long.valueOf(Math.abs(hashCode));
	}

}
