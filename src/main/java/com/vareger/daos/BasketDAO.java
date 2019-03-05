package com.vareger.daos;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.vareger.models.Basket;
import com.vareger.models.BasketStatus;
import com.vareger.models.Currency;
import com.vareger.requests.BasketParam;

@Repository
public class BasketDAO extends AbstractDao<Integer, Basket> {

	@SuppressWarnings("unchecked")
	public List<Basket> getByStatus(String status) {
		return createEntityCriteria().add(Restrictions.eq("status", status)).list();
	}

	public Basket getBySalt(Long salt) {
		return (Basket) createEntityCriteria().add(Restrictions.eq("salt", salt)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Basket> findAllBy24Hour() {
		long time = System.currentTimeMillis() - (24 * 60 * 60 * 1000);
		Date oneDayAgo = new Date(time);
		return createEntityCriteria().add(Restrictions.ge("openTime", oneDayAgo)).addOrder(Order.asc("openTime")).list();
	}

	@SuppressWarnings("unchecked")
	public List<Basket> findFinishedEager(int limit) {
		List<Basket> baksets = createEntityCriteria().add(Restrictions.eq("status", BasketStatus.FINISHED)).addOrder(Order.desc("finishTime"))
				.setMaxResults(limit).list();
		for (Basket b : baksets)
			Hibernate.initialize(b.getUserBids());

		return baksets;
	}
	
	@SuppressWarnings("unchecked")
	public List<Basket> findAllBy24Hour(int offset, int limit) {
		long time = System.currentTimeMillis() - (24 * 60 * 60 * 1000);
		Date oneDayAgo = new Date(time);
		return createEntityCriteria().add(Restrictions.ge("openTime", oneDayAgo)).setFirstResult(offset).setMaxResults(limit)
				.addOrder(Order.asc("openTime")).list();
	}

	@SuppressWarnings("unchecked")
	public List<Basket> findNotFinished() {
		return createEntityCriteria().add(Restrictions.neOrIsNotNull("status", BasketStatus.FINISHED)).addOrder(Order.asc("openTime")).list();
	}

	@SuppressWarnings("unchecked")
	public List<Basket> findByParam(Currency currency, Currency baseCurrency, BasketParam param) {
		Criteria criteria = prepareCriteriaWithouStatus(currency, baseCurrency, param);

		BasketStatus status = param.getState();
		if (status != null)
			criteria = criteria.add(Restrictions.eq("status", status));

		return criteria.addOrder(Order.desc("openTime")).list();
	}

	@SuppressWarnings("unchecked")
	public List<Basket> findByParamFrontView(Currency currency, Currency baseCurrency, BasketParam param) {
		Criteria criteria = prepareCriteriaWithouStatus(currency, baseCurrency, param);
		BasketStatus status = param.getState();

		if (status != null) {
			Date currTime = new Date();
			if (status == BasketStatus.OPENED) {
				criteria = criteria.add(Restrictions.le("openTime", currTime));
				criteria = criteria.add(Restrictions.ge("closeTime", currTime));
			} else if (status == BasketStatus.CLOSED) {
				criteria = criteria.add(Restrictions.le("closeTime", currTime));
				criteria = criteria.add(Restrictions.ge("finishTime", currTime));
			} else if (status == BasketStatus.FINISHED) {
				criteria = criteria.add(Restrictions.le("finishTime", currTime));
			}
		} else {
			criteria = criteria.add(Restrictions.eq("status", status));
		}

		return criteria.addOrder(Order.desc("openTime")).list();
	}

	private Criteria prepareCriteriaWithouStatus(Currency currency, Currency baseCurrency, BasketParam param) {
		Criteria criteria = createEntityCriteria();

		Date from = param.getFrom();
		Date to = param.getTo();
		Long lifeTime = param.getLifeTime();
		Long waitTime = param.getWaitTime();
		Long liveSeconds = null;
		Long openSeconds = null;
		Integer offset = param.getOffset();
		Integer limit = param.getLimit();

		if (lifeTime != null && waitTime != null) {
			liveSeconds = lifeTime + waitTime;
			openSeconds = lifeTime;
		}

		if (currency != null)
			criteria = criteria.add(Restrictions.eq("currency", currency));
		if (baseCurrency != null)
			criteria = criteria.add(Restrictions.eq("baseCurrency", baseCurrency));
		if (liveSeconds != null)
			criteria = criteria.add(Restrictions.eq("liveSeconds", liveSeconds));
		if (liveSeconds != null)
			criteria = criteria.add(Restrictions.eq("openSeconds", openSeconds));
		if (from != null)
			criteria = criteria.add(Restrictions.ge("openTime", from));
		if (to != null)
			criteria = criteria.add(Restrictions.le("openTime", to));
		if (offset != null)
			criteria = criteria.setFirstResult(offset);
		if (limit != null)
			criteria = criteria.setMaxResults(limit);

		return criteria;
	}
}
