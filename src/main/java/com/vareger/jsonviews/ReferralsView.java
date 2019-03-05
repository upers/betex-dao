package com.vareger.jsonviews;

import java.math.BigInteger;
import java.util.List;

import com.vareger.models.ReferralIncome;

public class ReferralsView {
	
	private BigInteger profit;
	
	private Integer amount;
	
	private List<ReferralIncome> list;
	
	private List<ChartReferralsView> chartList;
	
	public ReferralsView() {
		
	}
	
	public ReferralsView(List<ReferralIncome> list) {
		if (list == null)
			return;
		
		this.profit = BigInteger.valueOf(0);
		for (ReferralIncome income : list)
			this.profit.add(income.getProfit());
		
		this.amount = list.size();
		this.list = list;
	}

	public BigInteger getProfit() {
		return profit;
	}

	public void setProfit(BigInteger profit) {
		this.profit = profit;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public List<ReferralIncome> getList() {
		return list;
	}

	public void setList(List<ReferralIncome> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "ReferralsView [profit=" + profit + ", amount=" + amount + ", list=" + list + "]";
	}

	public List<ChartReferralsView> getChartList() {
		return chartList;
	}

	public void setChartList(List<ChartReferralsView> chartList) {
		this.chartList = chartList;
	}
}
