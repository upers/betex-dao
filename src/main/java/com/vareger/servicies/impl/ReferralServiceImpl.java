package com.vareger.servicies.impl;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.ReferralDAO;
import com.vareger.models.Referral;
import com.vareger.models.ReferralStatistic;
import com.vareger.models.User;
import com.vareger.models.UserBid;
import com.vareger.servicies.ReferralService;
import com.vareger.validators.AddressValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class ReferralServiceImpl extends ServiceImpl<Integer, Referral> implements ReferralService {

	private ReferralDAO referralDAO;

	@Autowired
	public ReferralServiceImpl(@Qualifier("referralDAO") AbstractDao<Integer, Referral> referralDAO) {
		super(referralDAO);
		this.referralDAO = (ReferralDAO) referralDAO;
	}

	/**
	 * Create referral to founder submitted as parameter
	 * founder
	 * referralAddress
	 */
	public void createReferral(User founder, String referralAddress) {
		if (founder.getId() == null)
			throw new IllegalStateException("Founder id MUST not be null");

		Referral referral = new Referral();
		referral.setAddress(AddressValidator.getHexClear(referralAddress));
		referral.setFounder(founder);
		referral.setRegistrationTime(new Date());

		referralDAO.save(referral);
	}

	/**
	 * Check is referral address mapped to founder submitted as parameter.
	 * founder
	 * referralAddress
	 * @return
	 */
	public boolean checkFounder(User founder, String referralAddress) {
		if (founder.getId() == null)
			throw new IllegalStateException("Founder id MUST not be null");

		return referralDAO.checkFounder(founder, referralAddress);
	}

	public List<UserBid> getReferralBidsInFinishRound(Referral referral, long from) {
		return referralDAO.getReferralBidsInFinishRound(referral, from);
	}

	public List<Referral> findByAddress(String address) {
		String clearHex = AddressValidator.getHexClear(address);
		return referralDAO.findByAddress(clearHex);
	}

	public boolean hasFounderForBrokerId(String address, Integer brokerId) {
		String clearHex = AddressValidator.getHexClear(address);
		List<Referral> referrals = referralDAO.findByAddress(clearHex);
		for (Referral referral: referrals) {
			Integer _brokerId = referral.getFounder().getBroker().getId();
			if (brokerId.equals(_brokerId))
				return true;

		}

		return false;
	}

}
