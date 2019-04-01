package com.vareger.servicies;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.vareger.dto.UserBidDto;
import com.vareger.models.UserBid;

public interface UserBidService extends Service<Integer, UserBid>{

	UserBid addUserBid(UserBidDto userBidDto);

	List<UserBid> getByUserAddress(String userAddress);
	
	List<UserBid> getActiveByUserAddress(String userAddress);
	
	UserBid rewardUser(Long salt, UserBid userBid);
	
	List<UserBid> getByBasketSalt(Long salt);
	
	UserBid saveOrUpdate(Long salt, UserBid userBid);
	
	UserBid addBasketBeforSave(Long salt, UserBid userBid);
	
	List<UserBid> getActive();
	
	List<UserBid> getFromSpecifiedDate(Date from);
	
	List<UserBid> findSuccessBidsFromSpecifiedDate(Date from);

	/**
	 * Get finshed users bids.
	 * @param addresses users address
	 * @param from user bid from which the search starts
	 * @return List of users bids
	 */
	List<UserBid> getSuccessByUserAddresses(List<String> addresses, UserBid from);

	List<UserBid> getAllFromBaskets(Collection<Integer> basketsIds);
}
