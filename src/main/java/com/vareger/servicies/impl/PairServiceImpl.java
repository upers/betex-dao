package com.vareger.servicies.impl;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.CurrencyDAO;
import com.vareger.daos.PairDAO;
import com.vareger.models.Currency;
import com.vareger.models.Pair;
import com.vareger.servicies.PairService;

import javax.transaction.NotSupportedException;

@Service
@Transactional
public class PairServiceImpl extends ServiceImpl<Integer, Pair> implements PairService {
	
	private PairDAO pairDao;
	
	@Autowired
	private CurrencyDAO currencyDAO;
	
	@Autowired
	public PairServiceImpl(@Qualifier("pairDAO") AbstractDao<Integer, Pair> pairDao) {
		super(pairDao);
		this.pairDao = (PairDAO)pairDao;
	}

	@Override
	public void updatePairs(List<SimpleEntry<String, String>> pairs) throws NotSupportedException {
		throw new NotSupportedException("This method is not supported now");
//		if (pairs == null)
//			return;
//
//		for (Pair pair : pairDao.findAll())
//			pairDao.delete(pair);
//
//		for (SimpleEntry<String, String> entry : pairs) {
//			String currencyCode = entry.getKey();
//			String baseCurrencyCode = entry.getValue();
//
//			Currency currency = currencyDAO.findByCode(currencyCode);
//			Currency baseCurrency = currencyDAO.findByCode(baseCurrencyCode);
//			if (currency != null && baseCurrency != null) {
//				Pair pair = new Pair();
//				pair.setCurrency(currency);
//				pair.setBaseCurrency(baseCurrency);
//
//				pairDao.save(pair);
//			}
//		}
	}

	@Override
	public boolean isPairPressent(Currency currency, Currency baseCurrency) {
		return pairDao.isPairPressent(currency, baseCurrency);
	}
	
	@Override
	public Pair findByCodes(String currCode, String baseCurrCode) {
		return pairDao.findByCodes(currCode, baseCurrCode);
	}

}
