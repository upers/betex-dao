package com.vareger.servicies;

import java.util.Date;
import java.util.List;

import com.vareger.models.ReferralIncome;

public interface ReferralIncomeService extends Service<Integer, ReferralIncome> {
	/**
	 * Select income from DB and count profit for each referral for founder.<br>
	 * code
	 * @return
	 */
	List<ReferralIncome> findByFounderGroupByRef(String code);
	
	/**
	 * Select incomes from DB by parameters.<br>
	 * Then count sum of profit in the each of ranges begin from: from(Date), to: from(Date) + interval.<br>
	 * founderAddress
	 * from
	 * to
	 * interval
	 * @return
	 */
	List<ReferralIncome> findByFounderAndInterval(String founderAddress, Date from, Date to, Long interval);
}
