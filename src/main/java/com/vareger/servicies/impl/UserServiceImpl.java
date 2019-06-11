package com.vareger.servicies.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.UserDAO;
import com.vareger.models.User;
import com.vareger.validators.AddressValidator;
import com.vareger.servicies.UserService;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<Integer, User> implements UserService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserDAO userDao;

	@Autowired
	public UserServiceImpl(@Qualifier("userDAO") AbstractDao<Integer, User> currecnyDao) {
		super(currecnyDao);
		this.userDao = (UserDAO) currecnyDao;
	}

	@Override
	public User getByAddressAndBrokerId(String publicKey, Integer brokerId) {
		String clearHex = AddressValidator.getHexClear(publicKey);
		
		return userDao.getByAddressAndBrokerId(clearHex, brokerId);
	}

	/**
	 * Merge user if he is not confirmed or not exist in data base.<br>
	 * Find user by ethereum public key.
	 * user
	 * @return false If user already confirmed
	 */
	public boolean mergeIfNotConfirmed(User user) {
		String clearHex = AddressValidator.getHexClear(user.getAddress());
		log.info("Clear hex: " + clearHex);
		User persistUser = userDao.getByAddressAndBrokerId(clearHex, user.getBroker().getId());
		if (persistUser == null) {
			user.setAddress(clearHex);
			userDao.save(user);

			return true;
		} else if (persistUser.getConfirmed() == false) {
			persistUser.setConfirmed(user.getConfirmed());
			persistUser.setAddress(clearHex);
			persistUser.setEmail(user.getEmail());

			return true;
		}

		return false;
	}

	@Override
	public User getByReferralSuffix(String referralSuffix) {
		return userDao.getByReferralSuffix(referralSuffix);
	}

	@Override
	public String getUserStatus(String publicKey, Integer brokerId) {
		User user = userDao.getByAddressAndBrokerId(publicKey, brokerId);

		String status = "confirmed";
		if (user == null)
			status = "none";
		else if (user.getConfirmed() == false)
			status = "nonconfirmed";

		return status;
	}


}
