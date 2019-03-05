package com.vareger.servicies;

import java.util.List;

import com.vareger.models.Murket;

public interface MurketService extends Service<Integer, Murket>{
	Murket findByName(String name);
	
	List<Murket> findAll();
	
	List<Murket> findAllEnabled();
}
