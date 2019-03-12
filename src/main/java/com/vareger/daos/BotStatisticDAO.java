package com.vareger.daos;

import com.vareger.models.BotStatistic;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

@Repository
public class BotStatisticDAO extends AbstractDao<Integer, BotStatistic> {

    public BotStatistic findLast() {
        return (BotStatistic) createEntityCriteria().setProjection(Projections.max("id")).uniqueResult();
    }
}
