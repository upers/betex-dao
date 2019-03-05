package com.vareger.redis.servicies;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

public abstract class RedisService<T, PK extends Serializable> {
	
	protected CrudRepository<T, PK> dao;
	
	
	public RedisService(CrudRepository<T, PK> dao) {
		this.dao = dao;
	}
	
	public void save(T t) {
		dao.save(t);
	}
	
	public void delete(PK pk) {
		dao.delete(pk);
	}
	
	public void delete(T t) {
		dao.delete(t);
	}
	
	public T findOne(PK pk) {
		return dao.findOne(pk);
	}
	
	public Iterable<T> findAll() {
		return dao.findAll();
	}
	
	public long count() {
		return dao.count();
	}
	
}
