package com.vareger.service.tools;

import org.hibernate.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;

import com.vareger.models.Basket;
import com.vareger.servicies.BasketService;

@Component
public class SafeBasketService {
	
	private static final Logger log = LoggerFactory.getLogger(SafeBasketService.class);
	
	@Autowired
	private BasketService basketService;
	
	/**
	 * This method will be execute while it will get access to serialize transaction mode.
	 *
	 * salt
	 * @return
	 */
	public Basket updateBySalt(Basket basket, Long salt) {
		try {
			System.out.println(basket);
			return basketService.updateBySalt(basket, salt);
		} catch (CannotCreateTransactionException | CannotAcquireLockException | TransactionSystemException | TransactionException e) {
			log.error(e.getMessage(), e);
			log.error("Could not serialize access to basket now. Try do transaction in 0.2 seconds.");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				log.error(e1.getMessage(), e1);
			}

			return updateBySalt(basket, salt);
		}
	}
}
