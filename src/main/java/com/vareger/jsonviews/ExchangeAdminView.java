package com.vareger.jsonviews;

import java.math.BigInteger;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vareger.models.Exchange;
import com.vareger.service.tools.Rounder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_EMPTY)
public class ExchangeAdminView extends ExchangeView {
	
	@Transient
	@JsonIgnore
	public static final BigInteger multiplicator = new BigInteger("100000000000000000");
	
	private Double totalSbtSupply;
	
	private Double amountD;
	
	public ExchangeAdminView() {}
	
	public ExchangeAdminView(Exchange exchange) {
		super(exchange);
		if (exchange.getTotalSupply() != null)
			this.totalSbtSupply = Rounder.roundDown(exchange.getTotalSupply().divide(multiplicator).doubleValue() / 10.0, 2);
		
		this.amountD = Rounder.roundDown(exchange.getAmount().divide(multiplicator).doubleValue() / 10.0, 2);
	}

	public Double getTotalSbtSupply() {
		return totalSbtSupply;
	}

	public void setTotalSbtSupply(Double totalSbtSupplu) {
		this.totalSbtSupply = totalSbtSupplu;
	}

	public Double getAmountD() {
		return amountD;
	}

	public void setAmountD(Double amountD) {
		this.amountD = amountD;
	}
}
