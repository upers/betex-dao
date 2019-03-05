package com.vareger.servicies;

import java.util.List;

import com.vareger.models.Server;

public interface ServerService extends Service<Integer, Server> {

	Server bookServer(String localServerIp);

	List<Server> getAllBusy();

}