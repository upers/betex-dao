package com.vareger.servicies;

import java.util.List;

import com.vareger.models.Broker;

public interface BrokerService extends Service<Integer, Broker>{
	List<Broker> getByAddress(String address);
}
