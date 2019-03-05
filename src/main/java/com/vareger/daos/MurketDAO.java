package com.vareger.daos;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.vareger.models.Murket;

@Repository
public class MurketDAO extends AbstractDao<Integer, Murket>{
	
	public Murket findByName(String name) {
		return (Murket) createEntityCriteria().add(Restrictions.eq("name", name)).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Murket> findAllEnabled() {
		return createEntityCriteria().add(Restrictions.eq("isEnabled", true)).list();
	}
}
