package com.vareger.servicies.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.BrokerDAO;
import com.vareger.models.Broker;
import com.vareger.servicies.BrokerService;

@Service
@Transactional
public class BrokerServiceImpl extends ServiceImpl<Integer, Broker> implements BrokerService {
	
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(BrokerServiceImpl.class);
	
	private BrokerDAO brokerDAO;

	public BrokerServiceImpl(AbstractDao<Integer, Broker> genericDao) {
		super(genericDao);
		brokerDAO = (BrokerDAO) genericDao;
	}
	
	public List<Broker> getByAddress(String address) {
		return brokerDAO.getByAddress(address);
	}


}
