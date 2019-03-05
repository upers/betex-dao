package com.vareger.daos;

import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.vareger.models.CapitalCoverage;

@Repository
public class CapitalCoverageDAO extends AbstractDao<Integer, CapitalCoverage>{
	
	@SuppressWarnings("unchecked")
	public List<CapitalCoverage> findAll(int limit) {
		return createEntityCriteria().addOrder(Order.desc("id")).setMaxResults(limit).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CapitalCoverage> findAll() {
		return createEntityCriteria().addOrder(Order.desc("id")).list();
	}
	
}
