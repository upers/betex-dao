package com.vareger.servicies.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.CapitalCoverageDAO;
import com.vareger.models.CapitalCoverage;
import com.vareger.servicies.CapitalCoverageService;

@Service
@Transactional
public class CapitalCoverageServiceImpl extends ServiceImpl<Integer, CapitalCoverage> implements CapitalCoverageService {
	
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(CapitalCoverageServiceImpl.class);
	
	private CapitalCoverageDAO totalCapitalDAO;

	public CapitalCoverageServiceImpl(AbstractDao<Integer, CapitalCoverage> genericDao) {
		super(genericDao);
		totalCapitalDAO = (CapitalCoverageDAO) genericDao;
	}
	
	public List<CapitalCoverage> findAll(int limit) {
		return totalCapitalDAO.findAll(limit);
	}

}
