package com.vareger.service.tools;

import org.hibernate.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;

import com.vareger.models.Server;
import com.vareger.servicies.ServerService;

@Component
public class SafeServerService {
	
	private static final Logger log = LoggerFactory.getLogger(SafeServerService.class);
	
	@Autowired
	private ServerService serverService;
	
	/**
	 * This method will be execute while it will get access to serialize transaction mode.
	 *
	 * salt
	 * @return
	 */
	public Server bookServer(String localServerIp) {
		Server server = null;
		try {
			server = serverService.bookServer(localServerIp);
		} catch (CannotCreateTransactionException | CannotAcquireLockException | TransactionSystemException | TransactionException e) {
			log.error(e.getMessage());
			log.error("Could not serialize access to servers now. Try do transaction in 0.2 seconds.");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				log.error(e1.getMessage(), e1);
			}
			
			bookServer(localServerIp);
		}
		
		return server;
	}
}
