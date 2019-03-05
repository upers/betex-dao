package com.vareger.daos;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.vareger.models.Server;
@Repository
public class ServerDAO extends AbstractDao<Integer, Server>{
	
	public Server bookServer(String localServerIp) {
		List<Server> servers = createEntityCriteria().add(Restrictions.eq("isBusy", new Boolean(false))).list();
		if (servers.isEmpty())
			return null;
		
		Server server = servers.get(0);
		server.setBusy(true);
		server.setLocalIpAddress(localServerIp);
		
		update(server);
		
		return server;
	}
	
	public List<Server> getAllBusy() {
		return createEntityCriteria().add(Restrictions.eq("isBusy", new Boolean(true))).list();
	}
	
}
