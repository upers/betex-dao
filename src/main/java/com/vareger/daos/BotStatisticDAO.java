package com.vareger.daos;

import com.vareger.models.BotStatistic;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BotStatisticDAO extends AbstractDao<Integer, BotStatistic> {

    public Long getCount() {
        return (Long) createEntityCriteria().setProjection(Projections.rowCount()).uniqueResult();
    }

    public BotStatistic findLast() {
        Integer id = (Integer) createEntityCriteria().setProjection(Projections.max("id")).uniqueResult();
        if (id != null)
            return findById(id);
        else
            return null;
    }

    public List<BotStatistic> findAll(int offset, int amount, Order order) {
        return createEntityCriteria().addOrder(order).setFirstResult(offset).setMaxResults(amount).list();
    }

}
