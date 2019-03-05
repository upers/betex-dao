package com.vareger.daos;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.vareger.models.Currency;

@Repository
public class CurrencyDAO extends AbstractDao<Integer, Currency>{
	
	public Currency findByCode(String code) {
		return (Currency) this.createEntityCriteria().add(Restrictions.eq("code", code)).uniqueResult();
	}
}
