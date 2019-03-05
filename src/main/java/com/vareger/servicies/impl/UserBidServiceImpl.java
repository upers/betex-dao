package com.vareger.servicies.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.BasketDAO;
import com.vareger.daos.UserBidDAO;
import com.vareger.models.Basket;
import com.vareger.models.BidType;
import com.vareger.models.UserBid;
import com.vareger.validators.AddressValidator;
import com.vareger.servicies.UserBidService;

@Service
@Transactional
public class UserBidServiceImpl extends ServiceImpl<Integer, UserBid> implements UserBidService {
	
	private final static Logger log = LoggerFactory.getLogger(UserBidServiceImpl.class);
	
	private UserBidDAO userBidDAO;

	@Autowired
	private BasketDAO basketDao;

	@Autowired
	public UserBidServiceImpl(@Qualifier("userBidDAO") AbstractDao<Integer, UserBid> genericDao) {
		super(genericDao);
		this.userBidDAO = (UserBidDAO) genericDao;
	}

	public List<UserBid> getByUserAddress(String userAddress) {
		String clearHex = AddressValidator.getHexClear(userAddress);

		return userBidDAO.getByUserAddress(clearHex);
	}
	
	@Override
	public List<UserBid> getActiveByUserAddress(String userAddress) {
		String clearHex = AddressValidator.getHexClear(userAddress);
		
		return userBidDAO.getActiveByUserAddress(clearHex);
	}
	
	@Override
	public UserBid addBasketBeforSave(Long salt, UserBid userBid) {
		String address = userBid.getAddress();
		BidType bidType = userBid.getBidType();
		String txHash = userBid.getTxHash();

		if (address == null)
			throw new IllegalArgumentException("Address hasn't be NULL");

		if (bidType == null)
			throw new IllegalArgumentException("BidType hasn't be NULL");

		if (salt == null)
			throw new IllegalArgumentException("Salt hasn't be NULL");

		if (txHash == null)
			throw new IllegalArgumentException("Tx hash hasn't be NULL");

		UserBid persistUserBid = userBidDAO.findByTxHash(txHash);
		if (persistUserBid != null)
			throw new IllegalStateException("User has already exist in DB.");

		Basket basket = basketDao.getBySalt(salt);
		if (basket == null)
			throw new IllegalStateException("User try bid on non exist basket salt: " + salt);

		userBid.setBasket(basket);
		
		return userBid;
	}
	
	@Override
	public UserBid saveOrGet(Long salt, UserBid userBid) {
		String txHash = userBid.getTxHash();
		if (txHash == null)
			throw new IllegalArgumentException("Tx hash hasn't be NULL");
		
		UserBid prersistUserBid = userBidDAO.findByTxHash(txHash);
		if (prersistUserBid != null)
			return prersistUserBid;
		
		addBasketBeforSave(salt, userBid);
		userBidDAO.save(userBid);
		
		return userBid;
	}

	@Transactional(
			isolation = Isolation.SERIALIZABLE
	)
	@Override
	public UserBid rewardUser(Long salt, UserBid userBid) {
		String address = userBid.getAddress();
		BidType bidType = userBid.getBidType();
		BigInteger totalWinAmount = userBid.getWinAmount();
		Date rewardDate = userBid.getRewardDate();
		BigInteger amount = userBid.getAmount();

		if (address == null)
			throw new IllegalArgumentException("Address hasn't be NULL");

		if (bidType == null)
			throw new IllegalArgumentException("BidType hasn't be NULL");

		if (salt == null)
			throw new IllegalArgumentException("Salt hasn't be NULL");

		if (amount == null)
			throw new IllegalArgumentException("Amount hasn't be NULL");
		
		address = AddressValidator.getHexClear(address);
		List<UserBid> persistUserBids = userBidDAO.findNotRewardedUsers(salt, address, bidType, amount);
		// Sum of users bids in basket with salt in method parameters
		
		log.debug("Not rewarded users amount: " + persistUserBids.size());
		for (UserBid bid : persistUserBids) {
			bid.setWinAmount(totalWinAmount);
			bid.setRewardDate(rewardDate);

			return bid;
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<UserBid> findSuccessBidsFromSpecifiedDate(Date from) {
		return userBidDAO.findSuccessBidsFromSpecifiedDate(from);
	}
	
	public List<UserBid> getByBasketSalt(Long salt) {
		return userBidDAO.getByBasketSalt(salt);
	}

	@Override
	public List<UserBid> getActive() {
		return userBidDAO.getActive();
	}

	@Override
	public List<UserBid> getFromSpecifiedDate(Date from) {
		return userBidDAO.getFromSpecifiedDate(from);
	}

}