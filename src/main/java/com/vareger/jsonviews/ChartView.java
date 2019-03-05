package com.vareger.jsonviews;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vareger.models.Rate;
import com.vareger.redis.models.Chart;

@JsonPropertyOrder({ "pairId", "rates" })
public class ChartView {
	
	private String pairId;
	
	private List<Rate> rates;
	
	public ChartView() {}
	
	public ChartView(Chart chart) {
		if (chart == null)
			return;
		
		String currCode = chart.getCurrencyCode();
		String baseCurrCode = chart.getBaseCurrencyCode();
		
		if (currCode != null && baseCurrCode != null)
			this.pairId = currCode + "_" + baseCurrCode;
		
		this.rates = chart.getRates();
		
	}
	public String getPairId() {
		return pairId;
	}

	public void setPairId(String pairId) {
		this.pairId = pairId;
	}

	public List<Rate> getRates() {
		return rates;
	}

	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}
}
