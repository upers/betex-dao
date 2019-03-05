package com.vareger.daos;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.data.repository.support.RepositoryInvocationInformation;
import org.springframework.stereotype.Repository;

import com.vareger.models.Basket;
import com.vareger.models.BidType;
import com.vareger.models.UserBid;

import jnr.ffi.Struct.blkcnt_t;

@Repository
public class UserBidDAO extends AbstractDao<Integer, UserBid> {

	@SuppressWarnings("unchecked")
	public List<UserBid> getByUserAddress(String userAddress) {
		return getSession().createCriteria(UserBid.class).add(Restrictions.eq("address", userAddress)).addOrder(Order.desc("id")).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserBid> getActiveByUserAddress(String userAddress) {
		return createEntityCriteria().add(Restrictions.eq("address", userAddress)).add(Restrictions.isNull("rewardDate"))
				.add(Restrictions.isNull("winAmount")).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserBid> getActive() {
		return createEntityCriteria().add(Restrictions.isNull("rewardDate"))
				.add(Restrictions.isNull("winAmount")).list();
	} 

	public UserBid findByTxHash(String txHash) {
		return (UserBid) createEntityCriteria().add(Restrictions.eq("txHash", txHash)).uniqueResult();
	}

	/**
	 * Find user by basket <i>salt</i> and <i>lastCheckedBid.address</i> if no fount create
	 * user and basket if needed.<br>
	 * If <i>UserBid.address</i> is NULL throw {@link IllegalStateException}<br>
	 * If <i>salt</i> is NULL throw {@link IllegalStateException} If <i>BidType</i>
	 * is NULL throw {@link IllegalStateException}
	 * 
	 * salt
	 * address
	 * bidType
	 * 
	 * @return {@link UserBid}
	 */
	@SuppressWarnings("unchecked")
	public List<UserBid> findNotRewardedUsers(Long salt, String address, BidType bidType, BigInteger amount) {
		if (address == null)
			throw new IllegalArgumentException("Address hasn't be NULL");

		if (salt == null)
			throw new IllegalArgumentException("Salt hasn't be NULL");

		if (bidType == null)
			throw new IllegalArgumentException("BidType hasn't be NULL");

		List<UserBid> bids = createEntityCriteria().createAlias("basket", "basket").add(Restrictions.eq("address", address))
				.add(Restrictions.eq("bidType", bidType)).add(Restrictions.eq("amount", amount)).add(Restrictions.eq("basket.salt", salt))
				.add(Restrictions.isNull("rewardDate")).add(Restrictions.isNull("winAmount")).list();

		return bids;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserBid> findSuccessBidsFromSpecifiedDate(Date from) {
		List<Integer> basketsId = ( (List<UserBid>)createEntityCriteria().add(Restrictions.ge("winAmount", BigInteger.ONE))
		.add(Restrictions.ge("bidDate", from)).list()).stream().map(UserBid::getBasket).map(Basket::getId).collect(Collectors.toList());
		
		return createEntityCriteria().add(Restrictions.in("basket.id", basketsId)).list();
	}


	@SuppressWarnings("unchecked")
	public List<UserBid> getByBasketSalt(Long salt) {
		return getSession().createCriteria(UserBid.class).createAlias("basket", "basket").add(Restrictions.eq("basket.salt", salt)).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserBid> getFromSpecifiedDate(Date from) {
		return createEntityCriteria().addOrder(Order.desc("id")).add(Restrictions.ge("bidDate", from)).list();
	}
	
}
