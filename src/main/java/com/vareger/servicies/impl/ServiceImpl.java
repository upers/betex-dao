package com.vareger.servicies.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.ReplicationMode;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.vareger.daos.AbstractDao;
import com.vareger.servicies.Service;


@Transactional
@org.springframework.stereotype.Service
public abstract class ServiceImpl<PK extends Serializable, T> implements Service<PK, T> {

	private AbstractDao<PK, T> abstractDAO;
	
	public ServiceImpl(AbstractDao<PK, T> genericDao) {
		this.abstractDAO = genericDao;
	}

	@Override
	public T findById(PK id) {
		return abstractDAO.findById(id);
	}

	@Override
	public void persist(T entity) {
		abstractDAO.persist(entity);
	}

	@Override
	public void merge(T entity) {
		abstractDAO.merge(entity);
	}

	@Override
	public void save(@Validated T entity) {
		abstractDAO.save(entity);
	}

	@Override
	public void saveOrUpdate(@Validated T entity) {
		abstractDAO.saveOrUpdate(entity);
	}

	@Override
	public void update(T entity) {
		abstractDAO.update(entity);
	}

	@Override
	public void delete(T entity) {
		abstractDAO.delete(entity);
	}

	@Override
	public void evict(T entity) {
		abstractDAO.evict(entity);
	}
	
	@Override
	public void replicate(T entity) {
		abstractDAO.replicate(entity);
	}
	
	@Override
	public void replicate(T entity, ReplicationMode replicateMode) {
		abstractDAO.replicate(entity, replicateMode);
	}

	@Override
	public List<T> findAll() {
		return abstractDAO.findAll();
	}
	
}
