package com.vareger.daos;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.vareger.models.Broker;
import com.vareger.validators.AddressValidator;

@Repository
public class BrokerDAO extends AbstractDao<Integer, Broker> {

	@SuppressWarnings("unchecked")
	public List<Broker> getByAddress(String address) {
		String clearHex = AddressValidator.getHexClear(address);
		
		return createEntityCriteria().add(Restrictions.eq("address", clearHex)).list();
	}
	
	@Override
	public void save(Broker broker) {
		String clearHex = AddressValidator.getHexClear(broker.getBrokerAddress());
		broker.setBrokerAddress(clearHex);
		super.save(broker);
	}
	
	@Override
	public void persist(Broker broker) {
		String clearHex = AddressValidator.getHexClear(broker.getBrokerAddress());
		broker.setBrokerAddress(clearHex);
		super.persist(broker);
	}
	
	@Override
	public void merge(Broker broker) {
		String clearHex = AddressValidator.getHexClear(broker.getBrokerAddress());
		broker.setBrokerAddress(clearHex);
		super.merge(broker);
	}
	
	@Override
	public void update(Broker broker) {
		String clearHex = AddressValidator.getHexClear(broker.getBrokerAddress());
		broker.setBrokerAddress(clearHex);
		super.update(broker);
	}
	
}
