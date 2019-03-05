package com.vareger.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.NetworkDAO;
import com.vareger.models.Network;
import com.vareger.servicies.NetworkService;

@Service
@Transactional
public class NetworkServiceImpl extends ServiceImpl<Integer, Network> implements NetworkService {
	
	private NetworkDAO networckDAO;
	
	@Autowired
	public NetworkServiceImpl(@Qualifier("networkDAO")AbstractDao<Integer, Network> genericDao) {
		super(genericDao);
		this.networckDAO = (NetworkDAO) genericDao;
	}

}