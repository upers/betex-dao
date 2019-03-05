package com.vareger.servicies.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.MurketDAO;
import com.vareger.models.Murket;
import com.vareger.servicies.MurketService;

@Service
@Transactional
public class MurketServiceImpl extends ServiceImpl<Integer, Murket> implements MurketService{
	
	private MurketDAO murketDAO;
	
	@Autowired
	public MurketServiceImpl(@Qualifier("murketDAO") AbstractDao genericDao) {
		super(genericDao);
		this.murketDAO = (MurketDAO) genericDao;
	}

	@Override
	public Murket findByName(String name) {
		return murketDAO.findByName(name);
	}
	
	public List<Murket> findAllEnabled() {
		return murketDAO.findAllEnabled();
	}

}
