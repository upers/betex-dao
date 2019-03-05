package com.vareger.jsonviews;

import java.util.Date;
import java.util.List;

import com.vareger.models.ReferralIncome;

public class ChartReferralsView {
	private Date from;
	
	private Date to;
	
	public ChartReferralsView() {}
	
	public ChartReferralsView(Date from, Date to, Long interval, String intervalStr, List<ReferralIncome> data) {
		this.from = from;
		this.to = to;
		this.interval = interval;
		this.intervalStr = intervalStr;
		this.data = data;
	}

	private Long interval;
	
	private String intervalStr;
	
	private List<ReferralIncome> data;

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Long getInterval() {
		return interval;
	}

	public void setInterval(Long interval) {
		this.interval = interval;
	}

	public String getIntervalStr() {
		return intervalStr;
	}

	public void setIntervalStr(String intervalStr) {
		this.intervalStr = intervalStr;
	}

	public List<ReferralIncome> getData() {
		return data;
	}

	public void setData(List<ReferralIncome> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ChartReferralsView [from=" + from + ", to=" + to + ", interval=" + interval + ", intervalStr=" + intervalStr + ", data=" + data + "]";
	}
	
	
}
