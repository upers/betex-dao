package com.vareger.servicies;

import com.vareger.models.Currency;

public interface CurrencyService extends Service<Integer, Currency> {
	public Currency findByCode(String code);
}
