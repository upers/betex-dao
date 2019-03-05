package com.vareger.servicies.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.ReferralIncomeDAO;
import com.vareger.models.ReferralIncome;
import com.vareger.validators.AddressValidator;
import com.vareger.servicies.ReferralIncomeService;

@Service
@Transactional
public class ReferralIncomeServiceImpl extends ServiceImpl<Integer, ReferralIncome> implements ReferralIncomeService {
	
	private ReferralIncomeDAO referralincomeDAO;
	
	@Autowired
	public ReferralIncomeServiceImpl(@Qualifier("referralIncomeDAO") AbstractDao<Integer, ReferralIncome> _referralincomeDAO) {
		super(_referralincomeDAO);
		this.referralincomeDAO = (ReferralIncomeDAO) _referralincomeDAO;
	}

	@Override
	public List<ReferralIncome> findByFounderGroupByRef(String address) {
		String clearHex = AddressValidator.getHexClear(address);
		List<ReferralIncome> resultList = new ArrayList<>();
		Map<String, List<ReferralIncome>> referralsGrouped = referralincomeDAO.findByFounder(clearHex).stream().collect( Collectors.groupingBy(ReferralIncome::getAddress));
		for (String referralAddress : referralsGrouped.keySet()) {
			BigInteger resultAmount = BigInteger.valueOf(0l);
			for (ReferralIncome income : referralsGrouped.get(referralAddress)) {
				resultAmount.add(income.getProfit());
			}
			
			resultList.add(new ReferralIncome(referralAddress, address, resultAmount));
		}
		
		return resultList;
	}
	
	@Override
	public List<ReferralIncome> findByFounderAndInterval(String founderAddress, Date from, Date to, Long interval) {
		String clearHex = AddressValidator.getHexClear(founderAddress);
		return referralincomeDAO.findByFounderAndInterval(clearHex, from, to, interval);
	}
	
}
