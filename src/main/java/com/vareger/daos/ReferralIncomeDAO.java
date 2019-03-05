package com.vareger.daos;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.vareger.models.ReferralIncome;
import com.vareger.validators.AddressValidator;

@Repository
public class ReferralIncomeDAO extends AbstractDao<Integer, ReferralIncome> {

	@SuppressWarnings("unchecked")
	public List<ReferralIncome> findByFounder(String founderAddress) {
		return this.createEntityCriteria().add(Restrictions.eq("founderAddress", founderAddress)).list();
	}
	
	/**
	 * Select incomes from DB by parameters.<br>
	 * Then count sum of profit in the each of ranges begin from: from(Date), to: from(Date) + interval.<br>
	 * founderAddress
	 * from
	 * to
	 * interval
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ReferralIncome> findByFounderAndInterval(String founderAddress, Date from, Date to, Long interval) {
		if (interval == null || interval < 1)
			throw new IllegalStateException("interval must be more then 1");
		if (from == null || to == null)
			throw new IllegalStateException("from(Date) and to(Date) must be not null");

		List<ReferralIncome> incomesDB = this.createEntityCriteria().add(Restrictions.eq("founderAddress", founderAddress)).add(Restrictions.between("timestamp", from, to))
				.addOrder(Order.asc("timestamp")).list();

		List<ReferralIncome> result = new ArrayList<>();

		long fromMillSec = from.getTime();
		long toMillSec = to.getTime();
		long intMillSec = interval * 1000l;
		
		int j = 0;
		for (long i = fromMillSec; i <= toMillSec; i += intMillSec) {
			long timeMillSec = (i + intMillSec <= toMillSec) ? i + intMillSec : toMillSec;
			Date currMinTime = new Date(i);
			Date currMaxTime = new Date(timeMillSec);
			
			ReferralIncome income = new ReferralIncome();
			income.setFounderAddress(founderAddress);
			income.setTimestamp(currMaxTime);
			
			BigInteger profit = BigInteger.valueOf(0);
			while ( j < incomesDB.size() ) {
				ReferralIncome currIn = incomesDB.get(j);
				Date currDate = currIn.getTimestamp();
				
				if (currDate.before(currMinTime))
					break;
				if (currDate.after(currMaxTime))
					break;
				
				profit = profit.add(currIn.getProfit());
				j += 1;
			}
			
			income.setProfit(profit);
			result.add(income);
		}

		return result;
	}
	
	@Override
	public void persist(ReferralIncome referralIncome) {
		String clearHexReff = AddressValidator.getHexClear(referralIncome.getAddress());
		String clearHexFounder = AddressValidator.getHexClear(referralIncome.getFounderAddress());
		referralIncome.setAddress(clearHexReff);
		referralIncome.setFounderAddress(clearHexFounder);
		
		getSession().persist(referralIncome);
	}
	
	@Override
	public void merge(ReferralIncome referralIncome) {
		String clearHexReff = AddressValidator.getHexClear(referralIncome.getAddress());
		String clearHexFounder = AddressValidator.getHexClear(referralIncome.getFounderAddress());
		referralIncome.setAddress(clearHexReff);
		referralIncome.setFounderAddress(clearHexFounder);
		
		getSession().merge(referralIncome);
	}
	
	@Override
	public void save(ReferralIncome referralIncome) {
		String clearHexReff = AddressValidator.getHexClear(referralIncome.getAddress());
		String clearHexFounder = AddressValidator.getHexClear(referralIncome.getFounderAddress());
		referralIncome.setAddress(clearHexReff);
		referralIncome.setFounderAddress(clearHexFounder);
		
		getSession().save(referralIncome);
	}
	
	@Override
	public void update(ReferralIncome referralIncome) {
		String clearHexReff = AddressValidator.getHexClear(referralIncome.getAddress());
		String clearHexFounder = AddressValidator.getHexClear(referralIncome.getFounderAddress());
		referralIncome.setAddress(clearHexReff);
		referralIncome.setFounderAddress(clearHexFounder);
		
		getSession().update(referralIncome);
	}
}
