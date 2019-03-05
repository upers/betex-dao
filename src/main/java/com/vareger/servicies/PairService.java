package com.vareger.servicies;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import com.vareger.models.Currency;
import com.vareger.models.Pair;

import javax.transaction.NotSupportedException;

public interface PairService extends Service<Integer, Pair>{
	void updatePairs(List<SimpleEntry<String, String>> pairs) throws NotSupportedException;
	
	boolean isPairPressent(Currency currency, Currency baseCurrency);
	
	Pair findByCodes(String currCode, String baseCurrCode);
}
