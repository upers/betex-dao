package com.vareger.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.CurrencyDAO;
import com.vareger.models.Currency;
import com.vareger.servicies.CurrencyService;

@Service
@Transactional
public class CurrencyServiceImpl extends ServiceImpl<Integer, Currency> implements CurrencyService {
	
	private CurrencyDAO currecnyDao;
	
	@Autowired
	public CurrencyServiceImpl(@Qualifier("currencyDAO") AbstractDao<Integer, Currency> currecnyDao) {
		super(currecnyDao);
		this.currecnyDao = (CurrencyDAO)currecnyDao;
	}

	@Override
	public Currency findByCode(String code) {
		return currecnyDao.findByCode(code);
	}
	
}
