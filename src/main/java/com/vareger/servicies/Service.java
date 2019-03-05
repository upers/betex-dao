package com.vareger.servicies;

import org.hibernate.ReplicationMode;

import java.io.Serializable;
import java.util.List;

public interface Service<PK extends Serializable, T> {
    T findById(PK id);

    void persist(T entity);

    void merge(T entity);

    void save(T entity);

    void saveOrUpdate(T entity);

    void update(T entity);

    void delete(T entity);

    void evict(T entity);

    void replicate(T entity);

    void replicate(T entity, ReplicationMode replicateMode);

    List<T> findAll();

}
