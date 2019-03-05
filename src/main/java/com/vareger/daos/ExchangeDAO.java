package com.vareger.daos;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.vareger.models.Exchange;
import com.vareger.web3j.EthNet;

@Repository
public class ExchangeDAO extends AbstractDao<String, Exchange> {

	@SuppressWarnings("unchecked")
	public List<Exchange> findByUserAddress(String userAddress) {
		return createEntityCriteria().add(Restrictions.eq("userAddress", userAddress)).list();
	}

	public Exchange findByQueryId(String queryId) {
		return (Exchange) createEntityCriteria().add(Restrictions.eq("queryId", queryId)).uniqueResult();
	}

	public Exchange findByUserTxHash(String userTxHash, EthNet ethNet) {
		return (Exchange) createEntityCriteria().add(Restrictions.eq("userTxHash", userTxHash)).add(Restrictions.eq("ethNet", ethNet)).uniqueResult();
	}

	public Exchange findByOraclizeTxHash(String oraclizeTxHash) {
		return (Exchange) createEntityCriteria().add(Restrictions.eq("oraclizeTxHash", oraclizeTxHash)).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Exchange> findAll() {
		return createEntityCriteria().addOrder(Order.desc("id")).list();
	}
}
