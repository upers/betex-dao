package com.vareger.servicies.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.ExchangeDAO;
import com.vareger.models.Exchange;
import com.vareger.validators.AddressValidator;
import com.vareger.web3j.EthNet;
import com.vareger.servicies.ExchangeService;

@Service
@Transactional
public class ExchangeServiceImpl extends ServiceImpl<String, Exchange> implements ExchangeService {

	private ExchangeDAO exchangeDAO;

	@Autowired
	public ExchangeServiceImpl(@Qualifier("exchangeDAO") AbstractDao<String, Exchange> currecnyDao) {
		super(currecnyDao);
		this.exchangeDAO = (ExchangeDAO) currecnyDao;
	}

	@Override
	public List<Exchange> findByUserAddress(String userAddress) {
		String clearHex = AddressValidator.getHexClear(userAddress);

		return exchangeDAO.findByUserAddress(clearHex);
	}

	@Override
	public Exchange mergeByUserTxHash(Exchange exchange, String userTxHash) {
		EthNet ethNet = exchange.getEthNet();
		if (userTxHash == null)
			throw new IllegalArgumentException("Tx hash hasn't be NULL");
		if (ethNet == null)
			throw new IllegalArgumentException("Ethereum network hasn't be NULL");
		
		Exchange persistExchange = exchangeDAO.findByUserTxHash(userTxHash, ethNet);

		if (persistExchange == null) {
			exchangeDAO.save(exchange);

			return exchange;
		}

		boolean confirmed = (exchange.isConfirmed() || persistExchange.isConfirmed()) ? true : false;
		exchange.setConfirmed(confirmed);

		persistExchange.merge(exchange);

		return persistExchange;
	}

	@Override
	public Exchange findByOraclizeTxHash(String oraclizeTxHash) {
		return exchangeDAO.findByOraclizeTxHash(oraclizeTxHash);
	}
	
}
