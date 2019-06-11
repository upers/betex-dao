package com.vareger.servicies;

import com.vareger.models.User;

public interface UserService extends Service<Integer, User> {
	User getByAddressAndBrokerId(String publicKey, Integer brokerId);

	String getUserStatus(String publicKey, Integer brokerId);

	boolean mergeIfNotConfirmed(User user);

	User getByReferralSuffix(String referralSuffix);

}
