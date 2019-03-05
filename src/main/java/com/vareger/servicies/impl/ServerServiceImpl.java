package com.vareger.servicies.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.ServerDAO;
import com.vareger.models.Server;
import com.vareger.servicies.ServerService;

@Service
@Transactional
public class ServerServiceImpl extends ServiceImpl<Integer, Server> implements ServerService {
	
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ServerServiceImpl.class);
	
	private ServerDAO serverDAO;

	public ServerServiceImpl(AbstractDao<Integer, Server> genericDao) {
		super(genericDao);
		serverDAO = (ServerDAO) genericDao;
	}

	@Override
	@Transactional(isolation=Isolation.SERIALIZABLE)
	public Server bookServer(String localServerIp) {
		Server server = serverDAO.bookServer(localServerIp);
		if (server == null) {
			List<Server> allServers = serverDAO.findAll();
			Server lastServer = allServers.get(allServers.size() - 1);
			int newId = lastServer.getId() + 1;
			
			server = new Server();
			server.setId(newId);
			server.setBusy(true);
			server.setLocalIpAddress(localServerIp);
			
			serverDAO.save(server);
		}
		
		return server;
	}
	
	@Override
	public List<Server> getAllBusy() {
		return serverDAO.getAllBusy();
	}


}
