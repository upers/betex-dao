package com.vareger.daos;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractDao<PK extends Serializable, T> {
	
	private final Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public AbstractDao(){
		this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	public T findById(PK id) {
		return (T) getSession().get(persistentClass, id);
	}

	public void persist(T entity) {
		getSession().persist(entity);
	}
	
	public void merge(T entity) {
		getSession().merge(entity);
	}

	public void saveOrUpdate(T t) {
		getSession().saveOrUpdate(t);
	}
	
	public void save(T entity) {
		getSession().save(entity);
	}
	
	public void update(T entity) {
		getSession().update(entity);
	}
	
	/**
	 * Default replicate mode is generate exception if detached object exist in DB.
	 * entity
	 */
	public void replicate(T entity) {
		getSession().replicate(entity, ReplicationMode.EXCEPTION);
	}
	
	public void replicate(T entity, ReplicationMode replicationMode) {
		getSession().replicate(entity, replicationMode);
	}

	public void delete(T entity) {
		getSession().delete(entity);
	}
	
	public void evict(T entity) {
		getSession().evict(entity);
	}
	
	protected Criteria createEntityCriteria(){
		return getSession().createCriteria(persistentClass);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Criteria criteria = createEntityCriteria();
		return (List<T>) criteria.list();
	}
	
}
