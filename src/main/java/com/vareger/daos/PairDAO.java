package com.vareger.daos;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.vareger.models.Currency;
import com.vareger.models.Pair;

@Repository
public class PairDAO extends AbstractDao<Integer, Pair> {

	public boolean isPairPressent(Currency currency, Currency baseCurrency) {
		Pair pair = (Pair) createEntityCriteria().add(Restrictions.eq("currency", currency))
				.add(Restrictions.eq("baseCurrency", baseCurrency)).uniqueResult();
		if (pair != null)
			return true;

		return false;
	}

	@SuppressWarnings("unchecked")
	public List<Pair> findAll() {
		return createEntityCriteria().addOrder(Order.asc("id")).list();
	}

	public Pair findByCodes(String currCode, String baseCurrCode) {
		return (Pair) createEntityCriteria().createAlias("currency", "curr").createAlias("baseCurrency", "baseCurr")
				.add(Restrictions.eq("curr.code", currCode)).add(Restrictions.eq("baseCurr.code", baseCurrCode)).uniqueResult();
	}
}
