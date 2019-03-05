package com.vareger.redis.models;

import java.util.HashMap;
import java.util.Map;

public class UserSocketInfo {
	
	private Integer amount;

	private String ip;

	private Map<String, String> subscriptions;
	
	private String serverId;
	
	private String userSession;
	
	public UserSocketInfo(String serverId, String clientIp, String userSession) {
		ip = clientIp;
		amount = 0;
		subscriptions = new HashMap<>();
		this.serverId = serverId;
		this.userSession = userSession;
	}

	public UserSocketInfo(String serverId, String userSession) {
		amount = 0;
		subscriptions = new HashMap<>();
		this.serverId = serverId;
		this.userSession = userSession;
	}
	
	public UserSocketInfo() {
		
	}

	public synchronized void put(String subId, String destenition) {
		amount += 1;
		subscriptions.put(subId, destenition);
	}

	public synchronized String remove(String subId) {
		String removedDes = subscriptions.remove(subId);
		if (removedDes != null)
			amount -= 1;
		
		return removedDes;
	}

	public int getAmount() {
		return amount;
	}

	public String getIp() {
		return ip;
	}

	public Map<String, String> getSubscriptions() {
		return subscriptions;
	}

	public String getServerId() {
		return serverId;
	}

	public String getUserSession() {
		return userSession;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setSubscriptions(Map<String, String> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public void setUserSession(String userSession) {
		this.userSession = userSession;
	}

}
