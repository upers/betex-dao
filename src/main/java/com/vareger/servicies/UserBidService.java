package com.vareger.servicies;

import java.util.Date;
import java.util.List;

import com.vareger.models.UserBid;

public interface UserBidService extends Service<Integer, UserBid>{
	
	List<UserBid> getByUserAddress(String userAddress);
	
	List<UserBid> getActiveByUserAddress(String userAddress);
	
	UserBid rewardUser(Long salt, UserBid userBid);
	
	List<UserBid> getByBasketSalt(Long salt);
	
	UserBid saveOrGet(Long salt, UserBid userBid);
	
	UserBid addBasketBeforSave(Long salt, UserBid userBid);
	
	List<UserBid> getActive();
	
	List<UserBid> getFromSpecifiedDate(Date from);
	
	List<UserBid> findSuccessBidsFromSpecifiedDate(Date from);
}
