package com.vareger.servicies;

import java.util.List;

import com.vareger.models.Basket;
import com.vareger.models.BasketWrapper;
import com.vareger.requests.BasketParam;

public interface BasketService extends Service<Integer, Basket>{
	
	List<Basket> getByStatus(String status);
	
	Basket updateBySalt(Basket basket, Long salt);
	
	void updateFinishRateBySalt(Long salt, Double resultRate);
	
	/**
	 * Check is basket exist. Check by input salt.<br>
	 * If input salt == null then method will return true
	 * salt
	 * @return
	 */
	boolean isSaltExist(Long salt);
	
	/**
	 * Check is basket exist by input salt .<br>
	 * If input salt == null then method will return true
	 * salt
	 * @return
	 */
	boolean isSaltExist(Basket basket);
	
	/**
	 * Generate unique salt and save basket to DB.<br>
	 * Exist chance to get get duplicate key exception.<br>
	 * Generate basket using current time + currCode + baseCurrCode + strikeRate.
	 *
	 */
	void generateSaltAndSave(Basket basket);
	/**
	 * Check is basket exist by input salt .<br>
	 * If input salt == null then method will return true
	 * salt
	 * @return
	 */	
	boolean isSaltExist(BasketWrapper basket);
	
	Basket getBySalt(Long salt);
	
	List<Basket> findAllBy24Hour();
	
	List<Basket> findAllBy24Hour(Integer limit, Integer offset);
	
	List<Basket> findByParam(BasketParam param);
	
	/**
	 * This method will be select baskets from DB by paramth.<br>
	 * But if there will be two baskets with ACTIVE status and with the same times and currencies<br>
	 * then only one with latest start_time will be returned.<br>
	 * This was done for frontend application.
	 * param
	 * @return
	 */
	List<Basket> findByParamFrontView(BasketParam param);
	
	List<Basket> findNotFinished();
	
	/**
	 * This method find finished basket.<br>
	 * End fetch all bids in Eager mode.
	 * @return
	 */
	List<Basket> findFinishedEager(int limit);
	
}
